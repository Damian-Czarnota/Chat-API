package com.chat.api.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(Long id) {
        this.users = this.users.stream()
                .filter(user -> !user.getID().equals(id))
                .collect(Collectors.toList());

    }
}
