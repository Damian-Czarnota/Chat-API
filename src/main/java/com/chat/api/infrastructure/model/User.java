package com.chat.api.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;

    @JsonIgnore
    @Size(max = 60)
    private String password;
    private String email;
    private String username;
    private byte[] profileImage;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

    public User() {}
}
