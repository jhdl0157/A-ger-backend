package com.ireland.ager.board.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.BoardUrl;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder

/**
 * @Class : BoardResponse
 * @Description : 게시판 도메인에 대한 Response DTO
 **/
public class BoardResponse {
    Long boardId;
    String title;
    String content;
    Long boardViewCnt;
    List<BoardUrl> urlList;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    boolean isOwner;

    /**
     * @Method : toBoardResponse
     * @Description : 게시판 데이터 응답 객체화
     * @Parameter : [board, account]
     * @Return : BoardResponse
     **/
    public static BoardResponse toBoardResponse(Board board, Account account) {
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .boardViewCnt(board.getBoardViewCnt())
                .urlList(board.getUrlList())
                .createAt(board.getCreatedAt())
                .updateAt(board.getUpdatedAt())
                .isOwner(board.getAccountId().equals(account))
                .build();
    }
}
