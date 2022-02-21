package com.ireland.ager.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Class : Product
 * @Description : 상품도메인에 대한 엔티티
 **/
@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "productId", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String productPrice;

    private String productDetail;
    private Long productViewCnt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String thumbNailUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId", orphanRemoval = true)
    private List<Url> urlList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * @Method : addAccount
     * @Description : 계정 엔티티와 양방향 맵핑
     * @Parameter : [updateAccount]
     * @Return : null
     **/
    public void addAccount(Account updateAccount) {
        this.setAccount(updateAccount);
    }

    /**
     * @Method : addUrl
     * @Description : Url 엔티티와 양방량 맵핑
     * @Parameter : [url]
     * @Return : null
     **/
    public void addUrl(Url url) {
        this.getUrlList().add(url);
        url.setProductId(this);
    }

    /**
     * @Method : deleteUrl
     * @Description : Url 엔티티 삭제
     * @Parameter : []
     * @Return : null
     **/
    public void deleteUrl() {
        for (Iterator<Url> it = this.getUrlList().iterator(); it.hasNext(); ) {
            Url url = it.next();
            url.setProductId(null);
            it.remove();
        }
    }
}