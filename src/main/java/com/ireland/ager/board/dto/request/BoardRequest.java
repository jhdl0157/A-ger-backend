package com.ireland.ager.board.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.BoardUrl;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Class : BoardRequest
 * @Description : 게시판 도메인에 대한 Request DTO
 **/
@Data
public class BoardRequest {

    @NotBlank(message = "3110")
    String title;

    @NotBlank(message = "3130")
    String content;
    
    /**
    * @Method : toBoard
    * @Description : 게시판 데이터 객체화
    * @Parameter : [boardRequest, account, uploadImgUrl]
    * @Return : Board
    **/
    public static Board toBoard(BoardRequest boardRequest, Account account, List<String> uploadImgUrl) {
        Board board = new Board();

        for (String str : uploadImgUrl) {
            BoardUrl url = new BoardUrl();
            url.setUrl(str);
            board.addUrl(url);
        }
        board.addAccount(account);
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setBoardViewCnt(0L);
        return board;
    }

    /**
    * @Method : toBoardUpdate
    * @Description : 게시판 정보 수정 데이터 객체화
    * @Parameter : [board, uploadImageUrl]
    * @Return : Board
    **/
    public Board toBoardUpdate(Board board,
                               List<String> uploadImageUrl) {
        for (String str : uploadImageUrl) {
            BoardUrl url = new BoardUrl();
            url.setUrl(str);
            board.addUrl(url);
        }
        board.setTitle(this.title);
        board.setContent(this.content);
        return board;
    }
}
