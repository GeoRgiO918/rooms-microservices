package com.georgiiHadzhiev.roomservice.service;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.roomservice.RoomCreatedPayload;
import com.georgiiHadzhiev.payloads.roomservice.RoomDeletedPayload;
import com.georgiiHadzhiev.payloads.roomservice.RoomUpdatedPayload;
import com.georgiiHadzhiev.payloads.roomservice.RoomViewedPayload;
import com.georgiiHadzhiev.roomservice.component.BrokerEventFactory;
import com.georgiiHadzhiev.roomservice.component.RoomMapper;
import com.georgiiHadzhiev.roomservice.dto.RoomApplicationEvent;
import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.entity.Room;
import com.georgiiHadzhiev.roomservice.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository repository;
    private final BrokerEventFactory eventProvider;
    private final RoomMapper mapper;
    private final ApplicationEventPublisher innerEventPublisher;

    public RoomService(RoomRepository repository, BrokerEventFactory eventProvider, RoomMapper mapper, ApplicationEventPublisher innerEventPublisher) {
        this.repository = repository;
        this.eventProvider = eventProvider;
        this.mapper = mapper;
        this.innerEventPublisher = innerEventPublisher;
    }

    @Transactional
    public RoomDto createRoom(RoomDto dto){

        Room room = mapper.toEntity(dto);
        room = repository.save(room);

        BaseEvent<RoomCreatedPayload> event = eventProvider.createRoomCreated(room);
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));

        return mapper.toDto(room);

    }

    @Transactional
    public RoomDto updateRoom(RoomDto dto){

        long id =dto.getId();
        Optional<Room> finded = repository.findById(id);

        if(finded.isEmpty()) throw new EntityNotFoundException();
        Room saved = mapper.toEntity(dto);

        Room oldRoom = new Room();
        BeanUtils.copyProperties(finded.get(), oldRoom); // старое состояние
        saved = repository.save(saved);

        BaseEvent<RoomUpdatedPayload> event = eventProvider.createRoomUpdated(oldRoom,saved);
        repository.flush();
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));
        return mapper.toDto(saved);
    }

    @Transactional
    public List<RoomDto> getAllRooms() {

        BaseEvent<RoomViewedPayload> event = eventProvider.createAllRoomViewed();
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));
        return repository.findAll()
                .stream()
                .map(r -> mapper.toDto(r))
                .toList();
    }

    @Transactional
    public RoomDto getRoomById(long id) {
        Optional<Room> found = repository.findById(id);
        if(found.isEmpty()) throw new EntityNotFoundException();
        BaseEvent<RoomViewedPayload> event = eventProvider.createRoomViewed(found.get());
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));
        return mapper.toDto(found.get());
    }

    @Transactional
    public RoomDto removeRoomById(long id){
        Optional<Room> found = repository.findById(id);

        if(found.isEmpty()) throw new EntityNotFoundException();
        Room deleted = found.get();
        repository.deleteById(id);
        BaseEvent<RoomDeletedPayload> event = eventProvider.createRoomDeleted(deleted);
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));

        return mapper.toDto(deleted);

    }


}
