package com.georgiiHadzhiev.roomservice.service;

import com.georgiiHadzhiev.entity.CrudEventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.roomservice.component.RoomEventProducer;
import com.georgiiHadzhiev.roomservice.component.RoomEventProvider;
import com.georgiiHadzhiev.roomservice.component.RoomMapper;
import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.entity.Room;
import com.georgiiHadzhiev.roomservice.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository repository;
    private RoomEventProvider eventProvider;
    private RoomEventProducer eventProducer;
    private RoomMapper mapper;

    public RoomService(RoomRepository repository, RoomEventProvider eventProvider, RoomEventProducer eventProducer, RoomMapper mapper) {
        this.repository = repository;
        this.eventProvider = eventProvider;
        this.eventProducer = eventProducer;
        this.mapper = mapper;
    }

    public RoomDto createRoom(RoomDto dto){

        Room room = mapper.toEntity(dto);
        room.setVersion(0L);
        room = repository.save(room);

        BaseEvent baseEvent = eventProvider.provide(room,CrudEventType.CREATED,"USER");
        eventProducer.publishRoomEvent(baseEvent);

        return mapper.toDto(room);

    }

    public RoomDto updateRoom(RoomDto dto){

        long id =dto.getId();
        Optional<Room> finded = repository.findById(id);

        if(finded.isEmpty()) throw new EntityNotFoundException();
        Room saved = mapper.toEntity(dto);
        saved = repository.save(saved);

        BaseEvent baseEvent = eventProvider.provide(saved,CrudEventType.UPDATED,"USER");
        eventProducer.publishRoomEvent(baseEvent);


        return mapper.toDto(saved);
    }

    public List<RoomDto> getAllRooms() {
        return repository.findAll()
                .stream()
                .map(r -> mapper.toDto(r))
                .toList();
    }

    public RoomDto getRoom(long id) {
        Optional<Room> finded = repository.findById(id);
        if(finded.isEmpty()) throw new EntityNotFoundException();
        return mapper.toDto(finded.get());
    }


}
