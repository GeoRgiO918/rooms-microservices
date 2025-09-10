package com.georgiiHadzhiev.roomservice.service;

import com.georgiiHadzhiev.entity.CrudEventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.roomservice.component.RoomEventProducer;
import com.georgiiHadzhiev.roomservice.component.RoomEventProvider;
import com.georgiiHadzhiev.roomservice.component.RoomMapper;
import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.entity.Room;
import com.georgiiHadzhiev.roomservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

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

    public RoomDto saveRoom(RoomDto dto){

        Room room = mapper.toEntity(dto);
        room.setVersion(0L);
        room = repository.save(room);

        BaseEvent baseEvent = eventProvider.provide(room,CrudEventType.CREATED,"USER");
        eventProducer.sendRoomEvent(baseEvent);

        return mapper.toDto(room);

    }
}
