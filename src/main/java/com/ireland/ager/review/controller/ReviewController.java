package com.ireland.ager.review.controller;


import com.ireland.ager.main.common.ListResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.service.ResponseService;
import com.ireland.ager.review.dto.request.ReviewRequest;
import com.ireland.ager.review.dto.response.ReviewResponse;
import com.ireland.ager.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : ReviewController
 * @Description : 리뷰 도메인에 대한 컨트롤러
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final ResponseService responseService;

    /**
     * @Method : postReview
     * @Description : 리뷰 등록
     * @Parameter : [accessToken, roomId, reviewRequest]
     * @Return : ResponseEntity<SingleResult<ReviewResponse>>
     **/
    @PostMapping("/{roomId}")
    public ResponseEntity<SingleResult<ReviewResponse>> postReview(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long roomId,
            @RequestPart(value = "review") ReviewRequest reviewRequest) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSingleResult
                (reviewService.postReview(roomId, reviewRequest, splitToken[1])), HttpStatus.CREATED);
    }
}