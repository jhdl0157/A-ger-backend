package com.ireland.ager.chat.repository;

import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Class : MessageRepositoryImpl
 * @Description : 메세지도메인에 대한 레포지토리
 **/
@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
}