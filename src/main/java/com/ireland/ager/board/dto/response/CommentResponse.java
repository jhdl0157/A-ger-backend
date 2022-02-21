package com.ireland.ager.board.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Class : CommentResponse
 * @Description : 댓글 도메인에 대한 Response DTO
 **/
@Data
@Builder
public class CommentResponse {
    Long commentId;
    Board boardId;
    Account accountId;
    String commentContent;
    LocalDateTime createAt;
    LocalDateTime updateAt;

    /**
     * @Method : toCommentResponse
     * @Description : 댓글 데이터 응답 객체화
     * @Parameter : [comment]
     * @Return : CommentResponse
     **/
    public static CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .boardId(comment.getBoardId())
                .accountId(comment.getAccountId())
                .commentContent(comment.getCommentContent())
                .createAt(comment.getCreatedAt())
                .updateAt(comment.getUpdatedAt())
                .build();
    }
}
