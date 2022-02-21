package com.ireland.ager.chat.service;

import com.amazonaws.services.ec2.model.transform.ModifyVpcEndpointConnectionNotificationResultStaxUnmarshaller;
import com.ireland.ager.chat.config.KafkaConstants;
import com.ireland.ager.chat.dto.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
 * @Class : KafkaProductService 
 * @Description : 채팅도메인에 대한 KafkaProductService
 **/
public class KafkaProductService {
    private final KafkaTemplate<String, MessageRequest> kafkaTemplate;

    /**
     * @Method : sendMessage
     * @Description :
     * @Parameter : [message]
     * @Return : null
     **/
    public void sendMessage(MessageRequest message) {
        System.out.println("send message : " + message);
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
