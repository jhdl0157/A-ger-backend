package com.ireland.ager.chat.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.entity.MessageRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @Class : MessageSummaryResponse
 * @Description : 메세지도메인에 대한 SummaryResponse DTO
 **/
@Getter
@Builder
public class MessageSummaryResponse {
    Long roomId;
    String accountNickname;
    String accountThumbnail;
    String latestMessage;
    LocalDateTime latestAt;

    /**
     * @Method : toMessageSummaryResponse
     * @Description : 간략한 메세지 데이터 응답객체화
     * @Parameter : [messageRoom, account, message]
     * @Return : MessageSummaryResponse
     **/
    public static MessageSummaryResponse toMessageSummaryResponse(MessageRoom messageRoom
            , Account account, Message message) {
        return MessageSummaryResponse.builder()
                .roomId(messageRoom.getRoomId())
                .accountNickname(account.getProfileNickname())
                .accountThumbnail(account.getProfileImageUrl())
                .latestMessage(message.getMessage())
                .latestAt(message.getCreatedAt())
                .build();
    }
}