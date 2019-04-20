package com.chat.api.infrastructure.controller;

import com.chat.api.infrastructure.boxes.messages.MessageBox;
import com.chat.api.infrastructure.service.classes.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/messages")
public class MessagesController {

    private final MessageService messageService;

    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/{roomId}")
    public ResponseEntity<?> get(@PathVariable String roomId) {
        return new ResponseEntity<>(messageService.getRoomMessages(Long.parseLong(roomId)), HttpStatus.OK);
    }

    @PostMapping(value = "/{roomId}")
    public ResponseEntity<?> create(@PathVariable String roomId, @RequestBody MessageBox messageBox) {
        messageService.createMessage(roomId, messageBox);
        return new ResponseEntity(HttpStatus.OK);
    }
}
