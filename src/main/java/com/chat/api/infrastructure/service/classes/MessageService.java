package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.messages.MessageBox;
import com.chat.api.infrastructure.dao.MessagesRepository;
import com.chat.api.infrastructure.dto.MessageDto;
import com.chat.api.infrastructure.model.Message;
import com.chat.api.infrastructure.service.interfaces.IMessageService;
import com.chat.api.security.SecurityUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    private final MessagesRepository messagesRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(MessagesRepository messagesRepository, SimpMessagingTemplate messagingTemplate) {
        this.messagesRepository = messagesRepository;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public List<MessageDto> getAll() {
        List<MessageDto> collection = markMessages(messagesRepository.findAll());
        return collection;
    }

    @Override
    public List<MessageDto> getRoomMessages(Long roomID) {
        List<MessageDto> collection = markMessages(messagesRepository.findAllByRoomId(roomID));
        return collection;
    }

    @Override
    public void createMessage(String roomId, MessageBox messageBox) {
        Message message = new Message(SecurityUtils.getCurrentUser(), messageBox.getContent(), Long.parseLong(roomId));
        messagesRepository.save(message);
        messagingTemplate.convertAndSend("/topic/messages/"+roomId, getAll());
    }

    private List<MessageDto> markMessages(List<Message> messages) {
        return messages
                .stream()
                .map(message -> new MessageDto(message))
                .collect(Collectors.toList());
    }
}
