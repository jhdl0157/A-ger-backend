package com.ireland.ager.review.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.review.entity.Review;
import lombok.Data;


/**
 * @Class : ReviewRequest
 * @Description : 리뷰 도메인에 대한 Request DTO
 **/
@Data
public class ReviewRequest {
    String comment;
    int stars;

    /**
     * @Method : toReview
     * @Description : 리뷰 정보 데이터 객체화
     * @Parameter : [reviewRequest, seller, product, buyer]
     * @Return : Review
     **/
    public static Review toReview(ReviewRequest reviewRequest, Account seller, Product product, Account buyer) {
        Review review = new Review();
        review.addAccount(seller);
        review.setComment(reviewRequest.comment);
        review.setStars(reviewRequest.stars);
        review.setBuyerNickname(buyer.getProfileNickname());
        review.setBuyerId(buyer.getAccountId());
        review.setSellerNickname(seller.getProfileNickname());
        review.setTitle(product.getProductName());
        return review;
    }

}
