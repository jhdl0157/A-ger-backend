package com.ireland.ager.review.service;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.exception.UnAuthorizedAccessException;
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

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl {
    private final MessageRoomRepository messageRoomRepository;
    private final ReviewRepository reviewRepository;
    private final AccountServiceImpl accountService;
    public ReviewResponse postReview(Long roomId, ReviewRequest reviewRequest, String accessToken) {
        MessageRoom messageRoom=messageRoomRepository.findById(roomId).orElseThrow(NotFoundException::new);
        Account accountByAccessToken = accountService.findAccountByAccessToken(accessToken); //구매자Account
        if (!Objects.equals(accountByAccessToken.getAccountId(), messageRoom.getBuyerId().getAccountId())) {
            throw new UnAuthorizedAccessException();
        }
        log.info("상태 확인 :",messageRoom.getReviewStatus());
        if(messageRoom.getReviewStatus().equals(ReviewStatus.SALE)){
            Review review=ReviewRequest.toReview(reviewRequest,messageRoom.getSellerId(),messageRoom.getProduct(),accountByAccessToken);
            messageRoom.setReviewStatus(ReviewStatus.POST);
            messageRoomRepository.save(messageRoom);
            reviewRepository.save(review);
            return ReviewResponse.toReviewResponse(review);
        }
        else throw new DuplicateReviewException();
    }

//    public List<ReviewResponse> findReviewList(Long accountId) {
//        messageRoomRepository.findById(accountId);
//
//        return
//    }
}
