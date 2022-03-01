package com.ireland.ager.main.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import static com.ireland.ager.main.entity.QSearch.search;

/**
 * @Class : BoardRepositoryImpl
 * @Description : 게시판 도메인에 대한 레파지토리
 **/
@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<String> findFirst5SearchesOrderByPopularDesc() {
        LocalDateTime now = LocalDateTime.now();
        List<String> searchJPAQuery = queryFactory
                .select(search.keyword)
                .from(search)
                .where(search.createdAt.between(now.minusMinutes(1),now))
                .groupBy(search.keyword)
                .orderBy(search.keyword.count().desc())
                .limit(5)
                .fetch();
        return searchJPAQuery;
    }
}
