package com.ireland.ager.chat.controller;

import com.ireland.ager.account.service.AuthServiceImpl;
import com.ireland.ager.chat.dto.response.MessageDetailsResponse;
import com.ireland.ager.chat.dto.response.MessageSummaryResponse;
import com.ireland.ager.chat.dto.response.RoomCreateResponse;
import com.ireland.ager.chat.service.MessageService;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.SliceResult;
import com.ireland.ager.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : MessageRoomController
 * @Description : 메세지룸도메인에 대한 컨트롤러
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class MessageRoomController {
    private final MessageService messageService;
    private final ResponseService responseService;

    /**
     * @Method : searchAllRoomList
     * @Description : 채팅방 리스트 조회
     * @Parameter : [accessToken, pageable]
     * @Return : ResponseEntity<SliceResult<MessageSummaryResponse>>
     **/
    @GetMapping
    public ResponseEntity<SliceResult<MessageSummaryResponse>> searchAllRoomList(
            @RequestHeader("Authorization") String accessToken
            , Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSliceResult(
                messageService.findRoomByAccessToken(splitToken[1], pageable)), HttpStatus.OK);
    }

    /**
     * @Method : roomEnter
     * @Description : 메세지룸 접속
     * @Parameter : [roomId, accessToken]
     * @Return : ResponseEntity<SingleResult<MessageDetailsResponse>>
     **/
    @GetMapping("/{roomId}")
    public ResponseEntity<SingleResult<MessageDetailsResponse>> roomEnter(
            @PathVariable Long roomId,
            @RequestHeader("Authorization") String accessToken
    ) {
        String[] splitToken = accessToken.split(" ");
        MessageDetailsResponse messageRoom = messageService.roomEnterByAccessToken(splitToken[1], roomId);
        return new ResponseEntity<>(responseService.getSingleResult(messageRoom), HttpStatus.OK);
    }

    /**
     * @Method : insertRoom
     * @Description : 메세지룸 생성
     * @Parameter : [productId, accessToken]
     * @Return : ResponseEntity<SingleResult<RoomCreateResponse>>
     **/
    @PostMapping("/{productId}")
    public ResponseEntity<SingleResult<RoomCreateResponse>> insertRoom(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String accessToken
    ) {
        String[] splitToken = accessToken.split(" ");
        RoomCreateResponse roomCreateResponse = messageService.insertRoom(productId, splitToken[1]);
        return new ResponseEntity<>(responseService.getSingleResult(roomCreateResponse), HttpStatus.CREATED);
    }

    /**
     * @Method : roomDelete
     * @Description : 메세지룸 삭제
     * @Parameter : [roomId, accessToken]
     * @Return : ResponseEntity<CommonResult>
     **/
    @DeleteMapping("/{roomId}")
    public ResponseEntity<CommonResult> roomDelete(
            @PathVariable Long roomId,
            @RequestHeader("Authorization") String accessToken
    ) {
        String[] splitToken = accessToken.split(" ");
        messageService.deleteById(splitToken[1], roomId);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
}
