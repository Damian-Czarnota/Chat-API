package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.messages.RoomBox;
import com.chat.api.infrastructure.boxes.responses.ResponseRoomUsers;
import com.chat.api.infrastructure.boxes.responses.ResponseRooms;
import com.chat.api.infrastructure.dao.RoomRepository;
import com.chat.api.infrastructure.dto.RoomDto;
import com.chat.api.infrastructure.dto.UserDto;
import com.chat.api.infrastructure.exceptions.DefaultException;
import com.chat.api.infrastructure.model.Room;
import com.chat.api.security.SecurityUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public RoomService(RoomRepository roomRepository, SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void create(RoomBox roomBox) {
        Room room = new Room(roomBox.getName());
        roomRepository.save(room);
        messagingTemplate.convertAndSend("/topic/rooms", getAll());
    }

    public ResponseRooms getAll() {
        List<RoomDto> collect = roomRepository.findAll().stream().map(room -> new RoomDto(room)).collect(Collectors.toList());
        return new ResponseRooms(collect);
    }

    public void connect(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new DefaultException("There is no room with this ID"));
        room.addUser(SecurityUtils.getCurrentUser());
        roomRepository.save(room);
        messagingTemplate.convertAndSend("/topic/rooms", getAll());
        messagingTemplate.convertAndSend("/topic/room/"+id+"/users", getUsers(id));
    }

    public void disconnect() {
        Room room = roomRepository.findByUsers_IDIn(SecurityUtils.getCurrentUser().getID());
        room.removeUser(SecurityUtils.getCurrentUser().getID());
        roomRepository.save(room);
        messagingTemplate.convertAndSend("/topic/rooms", getAll());
        messagingTemplate.convertAndSend("/topic/room/"+room.getID()+"/users", getUsers(room.getID()));
    }

    public ResponseRoomUsers getUsers(Long ID) {
        Room room = roomRepository.findById(ID).orElseThrow(() -> new DefaultException("Room with this id doesn't exist!"));
        List<UserDto> collect = room.getUsers().stream().map(user -> new UserDto(user)).collect(Collectors.toList());
        return new ResponseRoomUsers(collect);
    }
}
