package com.chat.api.infrastructure.boxes.responses;

import com.chat.api.infrastructure.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseRooms {
    private List<Room> rooms;
}
