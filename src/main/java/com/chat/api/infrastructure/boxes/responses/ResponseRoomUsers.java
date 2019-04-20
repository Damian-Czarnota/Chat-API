package com.chat.api.infrastructure.boxes.responses;

import com.chat.api.infrastructure.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseRoomUsers {
    private List<UserDto> users;
}
