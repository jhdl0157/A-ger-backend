package com.ireland.ager.product.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.entity.Url;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Class : ProductResponse
 * @Description : 상품도메인에 대한 Response DTO
 **/
@Data
@Builder
public class ProductResponse {
    Long productId;
    String productName;
    String productPrice;
    String productDetail;
    Long productViewCnt;
    Category category;
    ProductStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String thumbNailUrl;
    boolean isOwner = true;
    List<Url> urlList;

    /**
     * @Method : toProductResponse
     * @Description : 상품정보 데이터 응답객체화
     * @Parameter : [product, account]
     * @Return : ProductResponse
     **/
    public static ProductResponse toProductResponse(Product product, Account account) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDetail(product.getProductDetail())
                .productViewCnt(product.getProductViewCnt())
                .category(product.getCategory())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .thumbNailUrl(product.getThumbNailUrl())
                .isOwner(product.getAccount().equals(account))
                .urlList(product.getUrlList())
                .build();
    }
}