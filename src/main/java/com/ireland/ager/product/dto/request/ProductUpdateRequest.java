package com.ireland.ager.product.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.entity.Url;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @Class : ProductUpdateRequest
 * @Description : 상품도메인에 대한 UpdateRequest DTO
 **/
@Slf4j
@Data
public class ProductUpdateRequest {
    @NotBlank(message = "3010")
    String productName;
    @NotBlank(message = "3020")
    @Min(value = 0, message = "3021")
    String productPrice;
    @NotBlank(message = "3030")
    String productDetail;
    @NotBlank(message = "3040")
    String category;
    @NotBlank(message = "3050")
    String status;

    /**
     * @Method : toProductUpdate
     * @Description : 상품수정정보 데이터 객체화
     * @Parameter : [product, uploadImageUrl]
     * @Return : Product
     **/
    public Product toProductUpdate(Product product,
                                   List<String> uploadImageUrl) {
        for (String str : uploadImageUrl) {
            Url url = new Url();
            url.setUrl(str);
            product.addUrl(url);
        }
        product.setProductDetail(this.productDetail);
        product.setProductPrice(productPrice);
        product.setProductViewCnt(product.getProductViewCnt());
        product.setProductName(productName);
        product.setStatus(ProductStatus.valueOf(this.status));
        product.setCategory(Category.valueOf(this.category));
        return product;
    }
}