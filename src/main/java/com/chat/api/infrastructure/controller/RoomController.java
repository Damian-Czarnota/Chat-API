package com.chat.api.infrastructure.controller;

import com.chat.api.infrastructure.boxes.messages.RoomBox;
import com.chat.api.infrastructure.boxes.messages.RoomConnectBox;
import com.chat.api.infrastructure.boxes.responses.ResponseRooms;
import com.chat.api.infrastructure.service.classes.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody RoomBox roomBox){
        roomService.create(roomBox);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/connect")
    public ResponseEntity<?> connect(@RequestBody RoomConnectBox roomConnectBox) {
        roomService.connect(roomConnectBox.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/disconnect")
    public ResponseEntity<?> disconnect() {
        roomService.disconnect();
        return new ResponseEntity(HttpStatus.OK);
    }
}
