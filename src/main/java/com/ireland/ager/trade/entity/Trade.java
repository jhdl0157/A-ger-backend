package com.ireland.ager.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Product;
import lombok.*;

import javax.persistence.*;

/**
 * @Class : Trade
 * @Description : 거래 도메인에 대한 엔티티
 **/
@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "tradeId", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "buyer_id")
    private Account buyerId;

    /**
     * @Method : toTrade
     * @Description : 거래 요청 데이터 객체화
     * @Parameter : [product, buyerId]
     * @Return : Trade
     **/
    public static Trade toTrade(Product product, Account buyerId) {
        return Trade.builder()
                .product(product)
                .buyerId(buyerId)
                .build();
    }
}
