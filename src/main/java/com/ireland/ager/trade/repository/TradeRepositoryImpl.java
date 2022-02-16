package com.ireland.ager.trade.repository;


import com.ireland.ager.product.dto.response.ProductThumbResponse;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.trade.entity.Trade;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.ireland.ager.trade.entity.QTrade.trade;

/**
 * @Class : TradeRepositoryImpl
 * @Description : 거래 도메인에 대한 레파지토리
 **/
@Repository
@RequiredArgsConstructor
public class TradeRepositoryImpl implements TradeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * @Method : findBuyProductsByAccountId
     * @Description : 사용자 별 구매 상품 목록 조회
     * @Parameter : [accountId, pageable]
     * @Return : Slice<ProductThumbResponse>
     **/
    @Override
    public Slice<ProductThumbResponse> findBuyProductsByAccountId(Long accountId, Pageable pageable) {
        JPAQuery<Trade> tradeQuery = queryFactory
                .selectFrom(trade)
                .where(trade.buyerId.accountId.eq(accountId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1);
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(trade.getType(), trade.getMetadata());
            tradeQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<Trade> tradeList = tradeQuery.fetch();
        List<Product> productList = new ArrayList<>();
        for (Trade trade : tradeList) productList.add(trade.getProduct());
        List<ProductThumbResponse> content = new ArrayList<>(ProductThumbResponse.toProductListResponse(productList));
        boolean hasNext = false;

        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }
}
