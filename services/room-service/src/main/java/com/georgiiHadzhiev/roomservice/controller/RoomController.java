package com.georgiiHadzhiev.roomservice.controller;


import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.entity.Room;
import com.georgiiHadzhiev.roomservice.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {


    private RoomService roomService;

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }


    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto dto){
        RoomDto saved = roomService.createRoom(dto);
        URI location = URI.create("/rooms/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable Long id){
        RoomDto dto;
           try {
           dto = roomService.getRoom(id);
        }catch(EntityNotFoundException e){

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getRooms() {
        List<RoomDto> dtoList = roomService.getAllRooms();
        if (dtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id,@RequestBody RoomDto dto){

        dto.setId(id);
        try {
            dto = roomService.updateRoom(dto);
        }catch(EntityNotFoundException e){

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id){
//        Optional<Room> finded = roomRepository.findById(id);
//        if(finded.isEmpty()) return ResponseEntity.notFound().build();
//
//        roomRepository.deleteById(id);
//        roomEventProvider.roomDeleted(finded.get());
//
//        return ResponseEntity.noContent().build();
        return null;

    }

}
