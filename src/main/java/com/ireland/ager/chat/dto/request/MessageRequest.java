package com.ireland.ager.chat.dto.request;

import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.entity.MessageRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Class : MessageRequest
 * @Description : 메세지도메인에 대한 Request DTO
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest implements Serializable {
    private Long roomId;
    private Long senderId;
    private String message;

    /**
     * @Method : toMessage
     * @Description : 메세지 정보 데이터 객체화
     * @Parameter :  [messageDto, messageRoom]
     * @Return : Message
     **/
    public static Message toMessage(MessageRequest messageDto, MessageRoom messageRoom) {
        return Message.builder()
                .message(messageDto.getMessage())
                .senderId(messageDto.getSenderId())
                .messageRoom(messageRoom)
                .build();
    }
}
