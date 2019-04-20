package com.chat.api.infrastructure.dao;

import com.chat.api.infrastructure.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Message, Long> {
    List<Message> findAll();
    List<Message> findAllByRoomId(Long roomID);
}
