package com.chat.api.infrastructure.model;

import com.chat.api.infrastructure.enums.RoleName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
