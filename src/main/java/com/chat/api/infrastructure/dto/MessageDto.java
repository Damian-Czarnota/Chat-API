package com.chat.api.infrastructure.dto;

import com.chat.api.infrastructure.model.Message;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDto {
    private Long ID;
    private UserDto author;
    private Date date;
    private String content;
    private Long roomId;

    public MessageDto(Message message) {
        this.author = new UserDto(message.getAuthor());
        this.content = message.getContent();
        this.date = message.getDate();
        this.content = message.getContent();
        this.ID = message.getID();
        this.roomId = message.getRoomId();
    }
}
