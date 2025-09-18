package com.georgiiHadzhiev.roomservice.service;

import com.georgiiHadzhiev.entity.CrudEventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.roomservice.component.BrokerEventProvider;
import com.georgiiHadzhiev.roomservice.component.RoomMapper;
import com.georgiiHadzhiev.roomservice.dto.RoomApplicationEvent;
import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.entity.Room;
import com.georgiiHadzhiev.roomservice.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository repository;
    private final BrokerEventProvider eventProvider;
    private final RoomMapper mapper;
    private final ApplicationEventPublisher innerEventPublisher;

    public RoomService(RoomRepository repository, BrokerEventProvider eventProvider,RoomMapper mapper, ApplicationEventPublisher innerEventPublisher) {
        this.repository = repository;
        this.eventProvider = eventProvider;
        this.mapper = mapper;
        this.innerEventPublisher = innerEventPublisher;
    }

    @Transactional
    public RoomDto createRoom(RoomDto dto){

        Room room = mapper.toEntity(dto);
        room = repository.save(room);

        BaseEvent event = eventProvider.provide(room,CrudEventType.CREATED,"USER");
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));

        return mapper.toDto(room);

    }

    @Transactional
    public RoomDto updateRoom(RoomDto dto){

        long id =dto.getId();
        Optional<Room> finded = repository.findById(id);

        if(finded.isEmpty()) throw new EntityNotFoundException();
        Room saved = mapper.toEntity(dto);
        saved = repository.save(saved);

        BaseEvent event = eventProvider.provide(saved,CrudEventType.UPDATED,"USER");
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));

        repository.flush();
        return mapper.toDto(saved);
    }

    @Transactional
    public List<RoomDto> getAllRooms() {

        BaseEvent event = eventProvider.provideEmpty(CrudEventType.READ,"USER");
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
        BaseEvent event = eventProvider.provide(found.get(),CrudEventType.READ,"USER");
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));
        return mapper.toDto(found.get());
    }

    @Transactional
    public RoomDto removeRoomById(long id){
        Optional<Room> found = repository.findById(id);

        if(found.isEmpty()) throw new EntityNotFoundException();
        Room deleted = found.get();
        repository.deleteById(id);
        BaseEvent event = eventProvider.provide(deleted,CrudEventType.DELETED,"USER");
        innerEventPublisher.publishEvent(new RoomApplicationEvent(event));

        return mapper.toDto(deleted);

    }


}
