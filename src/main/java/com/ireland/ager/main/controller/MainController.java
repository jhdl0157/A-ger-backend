package com.ireland.ager.main.controller;

import com.ireland.ager.board.dto.response.BoardSummaryResponse;
import com.ireland.ager.board.service.BoardServiceImpl;
import com.ireland.ager.main.common.ListResult;
import com.ireland.ager.main.common.SliceResult;
import com.ireland.ager.main.common.service.ResponseService;
import com.ireland.ager.main.service.RedisService;
import com.ireland.ager.product.dto.response.ProductThumbResponse;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : MainController
 * @Description : 메인 도메인에 대한 컨트롤러
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class MainController {

    private final ProductServiceImpl productService;
    private final BoardServiceImpl boardService;
    private final ResponseService responseService;
    private final RedisService redisService;

    /**
     * @Method : searchAllProducts
     * @Description : 상품 검색
     * @Parameter : [accessToken, category, keyword, pageable]
     * @Return : ResponseEntity<SliceResult<ProductThumbResponse>>
     **/
    @GetMapping("/api/product/search")
    public ResponseEntity<SliceResult<ProductThumbResponse>> searchAllProducts(
            @RequestHeader("Authorization") String accessToken
            , @RequestParam(value = "category", required = false) Category category
            , @RequestParam(value = "keyword", required = false) String keyword
            , Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        redisService.postKeyword(splitToken[1], keyword);
        return new ResponseEntity<>(responseService.getSliceResult(
                productService.findProductAllByCreatedAtDesc(category, keyword, pageable)), HttpStatus.OK);
    }

    /**
     * @Method : searchAllBoards
     * @Description : 게시물 검색
     * @Parameter : [accessToken, keyword, pageable]
     * @Return : ResponseEntity<SliceResult<BoardSummaryResponse>>
     **/
    @GetMapping("/api/board/search")
    public ResponseEntity<SliceResult<BoardSummaryResponse>> searchAllBoards(
            @RequestHeader("Authorization") String accessToken
            , @RequestParam(value = "keyword", required = false) String keyword
            , Pageable pageable) {
        return new ResponseEntity<>(responseService.getSliceResult(
                boardService.findBoardAllByCreatedAtDesc(keyword, pageable)), HttpStatus.OK);
    }

    /**
     * @Method : searchAccountKeywords
     * @Description : 키워드 조회
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<ListResult<String>>
     **/
    @GetMapping("/api/keyword")
    public ResponseEntity<ListResult<String>> searchAccountKeywords(
            @RequestHeader("Authorization") String accessToken) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getListResult(
                redisService.getSearchList(splitToken[1])), HttpStatus.OK);
    }
}
