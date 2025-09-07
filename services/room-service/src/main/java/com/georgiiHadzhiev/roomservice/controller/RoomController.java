package com.georgiiHadzhiev.roomservice.controller;


import com.georgiiHadzhiev.roomservice.entity.Room;
import com.georgiiHadzhiev.roomservice.repository.RoomRepository;
import com.georgiiHadzhiev.roomservice.service.RoomEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private RoomRepository roomRepository;
    private RoomEventService roomEventService;

    public RoomController(RoomRepository roomRepository, RoomEventService roomEventService){
        this.roomRepository = roomRepository;
        this.roomEventService = roomEventService;
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room,
                                           Locale locale){
        Room saved = roomRepository.save(room);
        URI location = URI.create("/rooms/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id){
        return roomRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id,@RequestBody Room room){

        room.setId(id);
        Optional<Room> finded = roomRepository.findById(id);

        if(finded.isEmpty()) return ResponseEntity.notFound().build();

        Room saved = roomRepository.save(room);
        return ResponseEntity.ok(saved);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id){
        Optional<Room> finded = roomRepository.findById(id);
        if(finded.isEmpty()) return ResponseEntity.notFound().build();

        roomRepository.deleteById(id);
        roomEventService.roomDeleted(finded.get());

        return ResponseEntity.noContent().build();
    }

}
