package com.georgiiHadzhiev.roomservice.controller;


import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.service.RoomService;
import com.georgiiHadzhiev.utils.JsonLogger;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {


    private RoomService roomService;
    private final static JsonLogger log = new JsonLogger(RoomController.class);

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }


    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto dto){
        log.info("Received create request for room:", dto);
        RoomDto saved = roomService.createRoom(dto);
        log.info("Room created:",saved);
        URI location = URI.create("/rooms/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable Long id){
        log.info("Fetching room with id:",id);
        RoomDto dto;
           try {
           dto = roomService.getRoomById(id);
        }catch(EntityNotFoundException e){
            log.warn("Room with id {} not found",id);
            return ResponseEntity.notFound().build();
        }
        log.info("Fetched room:",dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getRooms() {
        log.info("Fetching all rooms",null);
        List<RoomDto> dtoList = roomService.getAllRooms();
        if (dtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id,@RequestBody RoomDto dto){
        log.info("Received update request for room",dto);
        dto.setId(id);
        try {
            dto = roomService.updateRoom(dto);
        }catch(EntityNotFoundException e){

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoomDto> deleteRoom(@PathVariable Long id){
        log.info("Received delete request for room with id",id);
        RoomDto dto;
        try{
            dto = roomService.removeRoomById(id);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);

    }

}
