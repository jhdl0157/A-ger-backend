package com.ireland.ager.board.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.Comment;
import lombok.Data;

/**
 * @Class : CommentRequest
 * @Description : 댓글 도메인에 대한 Request DTO
 **/
@Data
public class CommentRequest {

    String commentContent;

    /**
     * @Method : toComment
     * @Description : 댓글 데이터 객체화
     * @Parameter : [commentRequest, board, account]
     * @Return : Comment
     **/
    public static Comment toComment(CommentRequest commentRequest, Board board, Account account) {
        Comment comment = new Comment();
        comment.addAccount(account);
        comment.setBoardId(board);
        comment.setCommentContent(commentRequest.getCommentContent());
        return comment;
    }
}
