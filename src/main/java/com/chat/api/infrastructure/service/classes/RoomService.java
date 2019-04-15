package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.messages.RoomBox;
import com.chat.api.infrastructure.boxes.responses.ResponseRooms;
import com.chat.api.infrastructure.dao.RoomRepository;
import com.chat.api.infrastructure.model.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room create(RoomBox roomBox) {

        Room room = new Room(roomBox.getName());
        roomRepository.save(room);

        return room;
    }

    public ResponseRooms getAll() {
        return new ResponseRooms(roomRepository.findAll());
    }
}
