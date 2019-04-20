package com.chat.api.infrastructure.dao;

import com.chat.api.infrastructure.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAll();
    Optional<Room> findById(Long ID);

    Room findByUsers_IDIn(Long id);

}
