package com.ireland.ager.board.repository;

import com.ireland.ager.board.dto.response.BoardSummaryResponse;
import com.ireland.ager.board.entity.Board;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ireland.ager.board.entity.QBoard.board;
import static com.ireland.ager.board.entity.QBoardUrl.boardUrl;
import static com.ireland.ager.board.entity.QComment.comment;

/**
 * @Class : BoardRepositoryImpl
 * @Description : 게시판 도메인에 대한 레파지토리
 **/
@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @Method : findAllBoardPageableOrderByCreatedAtDesc
     * @Description : 모든 게시물 내림차순 조회
     * @Parameter : [keyword, pageable]
     * @Return : Slice<BoardSummaryResponse>
     **/
    @Override
    public Slice<BoardSummaryResponse> findAllBoardPageableOrderByCreatedAtDesc(String keyword, Pageable pageable) {
        JPAQuery<Board> boardJPAQuery = queryFactory
                .selectFrom(board)
                .where(keywordContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1);
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            boardJPAQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<Board> fetch = boardJPAQuery.fetch();
        List<BoardSummaryResponse> content = new ArrayList<>();
        for (Board board : fetch) {
            content.add(BoardSummaryResponse.toBoardSummaryResponse(board));
        }
        boolean hasNext = false;

        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    /**
     * @Method : findBoardsByAccountId
     * @Description : 계정 정보로 게시물 조회
     * @Parameter : [accountId, pageable]
     * @Return : Slice<BoardSummaryResponse>
     **/
    @Override
    public Slice<BoardSummaryResponse> findBoardsByAccountId(Long accountId, Pageable pageable) {
        JPAQuery<Board> boardQuery = queryFactory
                .selectFrom(board)
                .where(board.accountId.accountId.eq(accountId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1); //limit보다 한 개 더 들고온다.
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            boardQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<Board> fetch = boardQuery.fetch();
        List<BoardSummaryResponse> content = new ArrayList<>();
        for (Board board : fetch) {
            Long countComment = queryFactory
                    .selectFrom(comment)
                    .where(comment.boardId.boardId.eq(board.getBoardId()))
                    .fetchCount();
            content.add(BoardSummaryResponse.toBoardSummaryResponse(board));
        }
        boolean hasNext = false;
        //마지막 페이지는 사이즈가 항상 작다.
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    /**
     * @Method : addViewCntFromRedis
     * @Description : 레디스 서버 게시물 조회수 증가
     * @Parameter : [boardId, addCnt]
     * @Return : null
     **/
    @Override
    public void addViewCntFromRedis(Long boardId, Long addCnt) {
        queryFactory
                .update(board)
                .set(board.boardViewCnt, addCnt)
                .where(board.boardId.eq(boardId))
                .execute();
    }

    /**
     * @Method : addViewCnt
     * @Description : 게시물 조회수 증가
     * @Parameter : [boardId]
     * @Return : Board
     **/
    @Override
    public Board addViewCnt(Long boardId) {
        queryFactory
                .update(board)
                .set(board.boardViewCnt, board.boardViewCnt.add(1))
                .where(board.boardId.eq(boardId))
                .execute();
        return queryFactory
                .selectFrom(board)
                .leftJoin(board.urlList, boardUrl)
                .fetchJoin()
                .where(board.boardId.eq(boardId), board.eq(boardUrl.board))
                .fetchOne();
    }

    /**
     * @Method : keywordContains
     * @Description : 게시물 키워드 조회
     * @Parameter : [keyword]
     * @Return : BooleanExpression
     **/
    private BooleanExpression keywordContains(String keyword) {
        return ObjectUtils.isEmpty(keyword) ? null : board.title.contains(keyword);
    }
}
