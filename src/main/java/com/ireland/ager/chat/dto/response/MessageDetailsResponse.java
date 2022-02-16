package com.ireland.ager.chat.dto.response;

import com.ireland.ager.chat.entity.MessageRoom;
import lombok.Builder;
import lombok.Getter;

/**
 * @Class : MessageDetailsResponse
 * @Description : 메세지도메인에 대한 Response DTO
 **/
@Getter
@Builder
public class MessageDetailsResponse {
    Long roomId;
    String reviewStatus;

    /**
     * @Method : toMessageDetailsResponse
     * @Description : 메세지 상세정보 데이터 응답객체화
     * @Parameter : [messageRoom]
     * @Return : MessageDetailsResponse
     **/
    public static MessageDetailsResponse toMessageDetailsResponse(MessageRoom messageRoom) {
        return MessageDetailsResponse.builder()
                .roomId(messageRoom.getRoomId())
                .reviewStatus(messageRoom.getReviewStatus().name())
                .build();
    }
}