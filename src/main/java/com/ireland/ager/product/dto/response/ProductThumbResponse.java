package com.ireland.ager.product.dto.response;

import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class : ProductThumbResponse
 * @Description : 상품도메인에 대한 ThumbResponse DTO
 **/
@Data
@Builder
public class ProductThumbResponse {
    String productName;
    String productPrice;
    String productStatus;
    Long productViewCnt;
    Category category;
    LocalDateTime createdAt;
    String thumbNailUrl;

    /**
     * @Method : toProductThumbResponse
     * @Description : 간략한 상품정보 데이터 응답객체화
     * @Parameter : [product]
     * @Return : ProductThumbResponse
     **/
    public static ProductThumbResponse toProductThumbResponse(Product product) {
        return ProductThumbResponse.builder()
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productStatus(product.getStatus().name())
                .productViewCnt(product.getProductViewCnt())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .thumbNailUrl(product.getThumbNailUrl())
                .build();
    }

    /**
     * @Method : toProductListResponse
     * @Description : 간략한 상품정보 리스트 데이터 응답객체화
     * @Parameter : [productList]
     * @Return : List<ProductThumbResponse>
     **/
    public static List<ProductThumbResponse> toProductListResponse(List<Product> productList) {
        List<ProductThumbResponse> productResponseList = new ArrayList<>();
        for (Product product : productList) {
            ProductThumbResponse productResponse = ProductThumbResponse.toProductThumbResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }
}
