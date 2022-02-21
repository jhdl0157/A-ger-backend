package com.ireland.ager.board.controller;

import com.ireland.ager.board.dto.request.CommentRequest;
import com.ireland.ager.board.dto.response.CommentResponse;
import com.ireland.ager.board.repository.BoardRepository;
import com.ireland.ager.board.service.BoardServiceImpl;
import com.ireland.ager.board.service.CommentServiceImpl;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Class : CommentController
 * @Description : 댓글 도메인에 대한 컨트롤러
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class CommentController {

    private final BoardServiceImpl boardService;
    private final CommentServiceImpl commentService;
    private final BoardRepository boardRepository;
    private final ResponseService responseService;

    /**
     * @Method : createComment
     * @Description : 댓글 등록
     * @Parameter : [accessToken, commentRequest, boardId]
     * @Return : ResponseEntity<SingleResult<CommentResponse>>
     **/
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<SingleResult<CommentResponse>> createComment(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestPart(value = "comments") CommentRequest commentRequest,
                                                                       @PathVariable(value = "boardId") Long boardId) throws IOException {

        String[] splitToken = accessToken.split(" ");
        CommentResponse commentResponse = commentService.saveComment(splitToken[1], boardId, commentRequest);
        return new ResponseEntity<>(responseService.getSingleResult(commentResponse), HttpStatus.CREATED);
    }

    /**
     * @Method : updateComment
     * @Description : 댓글 수정
     * @Parameter : [accessToken, commentRequest, boardId, commentId]
     * @Return : ResponseEntity<SingleResult<CommentResponse>>
     **/
    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<SingleResult<CommentResponse>> updateComment(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestPart(value = "comments") CommentRequest commentRequest,
                                                                       @PathVariable(value = "boardId") Long boardId,
                                                                       @PathVariable(value = "commentId") Long commentId) throws IOException {

        String[] splitToken = accessToken.split(" ");
        CommentResponse commentResponse = commentService.updateComment(splitToken[1], commentId, commentRequest);
        return new ResponseEntity<>(responseService.getSingleResult(commentResponse), HttpStatus.OK);
    }

    /**
     * @Method : deleteComment
     * @Description : 댓글 삭제
     * @Parameter : [accessToken, boardId, commentId]
     * @Return : ResponseEntity<CommonResult>
     **/
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommonResult> deleteComment(@RequestHeader("Authorization") String accessToken,
                                                      @PathVariable(value = "boardId") Long boardId,
                                                      @PathVariable(value = "commentId") Long commentId) throws IOException {
        String[] splitToken = accessToken.split(" ");
        commentService.deleteComment(splitToken[1], commentId);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
}
