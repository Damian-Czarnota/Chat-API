package com.chat.api.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    private Date date;
    private String content;
    private Long roomId;

    public Message(User user, String content, Long roomId) {
        this.author = user;
        this.content = content;
        this.date = new Date();
        this.roomId = roomId;
    }
}
