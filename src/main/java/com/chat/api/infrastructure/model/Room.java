package com.chat.api.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;
    private String socketUrl;

    public Room(String name) {
        this.name = name;
    }
}
