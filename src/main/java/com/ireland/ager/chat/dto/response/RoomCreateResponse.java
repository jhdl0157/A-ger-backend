package com.ireland.ager.chat.dto.response;

import com.ireland.ager.chat.entity.MessageRoom;
import lombok.Builder;
import lombok.Getter;

/**
 * @Class : RoomCreateResponse
 * @Description : 메세지도메인에 대한 RoomResponse DTO
 **/
@Getter
@Builder
public class RoomCreateResponse {
    Long roomId;

    /**
     * @Method : toRoomCreateResponse
     * @Description : 메세지룸 데이터 응답 객체화
     * @Parameter :  [messageRoom]
     * @Return : RoomCreateResponse
     **/
    public static RoomCreateResponse toRoomCreateResponse(MessageRoom messageRoom) {
        return RoomCreateResponse.builder()
                .roomId(messageRoom.getRoomId())
                .build();
    }
}
