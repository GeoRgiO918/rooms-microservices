package com.georgiiHadzhiev.roomservice.repository;

import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

 }
