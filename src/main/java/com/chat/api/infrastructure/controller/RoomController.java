package com.chat.api.infrastructure.controller;

import com.chat.api.infrastructure.boxes.messages.RoomBox;
import com.chat.api.infrastructure.boxes.responses.ResponseRooms;
import com.chat.api.infrastructure.service.classes.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<?> get(){

        return new ResponseEntity<ResponseRooms>(roomService.getAll(), HttpStatus.OK);
    }

    @MessageMapping("/rooms/create")
    @SendTo("/topic/rooms")
    public ResponseRooms create(@RequestBody RoomBox roomBox){
        roomService.create(roomBox);
        return roomService.getAll();
    }
}
