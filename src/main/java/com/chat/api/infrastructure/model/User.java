package com.chat.api.infrastructure.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;
    private String password;
    private String email;
    private String login;

    @ManyToMany
    private Set<Role> roles;

}
