package com.ireland.ager.review.service;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.exception.UnAuthorizedAccessException;
import com.ireland.ager.account.repository.AccountRepository;
import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.chat.entity.MessageRoom;
import com.ireland.ager.chat.entity.ReviewStatus;
import com.ireland.ager.chat.repository.MessageRoomRepository;
import com.ireland.ager.main.exception.NotFoundException;
import com.ireland.ager.review.dto.request.ReviewRequest;
import com.ireland.ager.review.dto.response.ReviewResponse;
import com.ireland.ager.review.entity.Review;
import com.ireland.ager.review.exception.DuplicateReviewException;
import com.ireland.ager.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Class : ReviewServiceImpl
 * @Description : 리뷰 도메인에 대한 서비스
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl {
    private final MessageRoomRepository messageRoomRepository;
    private final ReviewRepository reviewRepository;
    private final AccountServiceImpl accountService;

    /**
     * @Method : postReview
     * @Description : 리뷰 등록
     * @Parameter : [roomId, reviewRequest, accessToken]
     * @Return : ReviewResponse
     **/
    public ReviewResponse postReview(Long roomId, ReviewRequest reviewRequest, String accessToken) {
        MessageRoom messageRoom = messageRoomRepository.findById(roomId).orElseThrow(NotFoundException::new);
        Account accountByAccessToken = accountService.findAccountByAccessToken(accessToken);
        if (!accountByAccessToken.getAccountId().equals(messageRoom.getBuyerId().getAccountId())) {
            throw new UnAuthorizedAccessException();
        }
        if (messageRoom.getReviewStatus().equals(ReviewStatus.SALE)) {
            Review review = ReviewRequest.toReview(reviewRequest, messageRoom.getSellerId(), messageRoom.getProduct(), accountByAccessToken);
            messageRoom.setReviewStatus(ReviewStatus.POST);
            messageRoomRepository.save(messageRoom);
            reviewRepository.save(review);
            return ReviewResponse.toReviewResponse(review);
        } else throw new DuplicateReviewException();
    }
}
