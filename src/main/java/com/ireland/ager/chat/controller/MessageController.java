package com.ireland.ager.chat.controller;

import com.ireland.ager.chat.dto.request.MessageRequest;
import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.service.KafkaProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : MessageController
 * @Description : 메세지도메인에 대한 컨트롤러
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class MessageController {
    private final KafkaProductService kafkaProductService;

    /**
     * @Method : sendMessage
     * @Description : 카프카 프로듀서에게 메세지 전달
     * @Parameter : [message]
     * @Return : void
     **/
    @PostMapping(value = "/publish")
    public void sendMessage(@RequestBody MessageRequest message) {
        kafkaProductService.sendMessage(message);
    }

    /**
     * @Method : broadcastGroupMessage
     * @Description : 프론트엔드로 메세지 전송
     * @Parameter : [roomId, message]
     * @Return : Message
     **/
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group/{roomId}")
    public Message broadcastGroupMessage(@DestinationVariable Long roomId, @Payload Message message) {
        return message;
    }
}