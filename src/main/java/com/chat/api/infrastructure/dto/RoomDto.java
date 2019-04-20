package com.chat.api.infrastructure.dto;

import com.chat.api.infrastructure.model.Room;
import lombok.Data;

@Data
public class RoomDto {
    private Long ID;
    private String name;
    private int numberOfUsers;

    public RoomDto(Room room) {
        this.name = room.getName();
        this.ID = room.getID();
        this.numberOfUsers = room.getUsers().size();
    }
}
