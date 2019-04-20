package com.chat.api.infrastructure.service.interfaces;

import com.chat.api.infrastructure.boxes.messages.MessageBox;
import com.chat.api.infrastructure.dto.MessageDto;

import java.util.List;

public interface IMessageService {

    List<MessageDto> getAll();

    List<MessageDto> getRoomMessages(Long roomID);

    public void createMessage(String roomId, MessageBox messageBox);
}
