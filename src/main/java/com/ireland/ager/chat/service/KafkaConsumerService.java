package com.ireland.ager.chat.service;

import com.ireland.ager.chat.config.KafkaConstants;
import com.ireland.ager.chat.dto.request.MessageRequest;
import com.ireland.ager.chat.entity.MessageRoom;
import com.ireland.ager.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
 * @Class : KafkaConsumerService
 * @Description : 메세지도메인에 대한 KafkaConsumerService
 **/
public class KafkaConsumerService {
    private final SimpMessagingTemplate template;
    private final MessageRepository messageRepository;
    private final MessageService messageService;

    /**
     * @Method : listen
     * @Description : Producer에서 생성된 메시지 받고 converAndSend로 메시지 전송
     * @Parameter : [message]
     * @Return : null
     **/
    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void listen(MessageRequest message) {
        log.info("sending via kafka listener..");
        //여기서 MessageRequest를 Message로 변환하고 정보 전달을 한다,,?
        MessageRoom messageRoom = messageService.findByRoomId(message.getRoomId());
        messageRepository.save(message.toMessage(message, messageRoom));
        template.convertAndSend("/topic/group/" + messageRoom.getRoomId(), message);
    }

}
