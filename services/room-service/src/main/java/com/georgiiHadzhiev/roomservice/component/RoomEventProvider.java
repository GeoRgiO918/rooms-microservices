package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.entity.CrudEventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RoomEventProvider {



    private RoomEventProducer roomEventProducer;

    public RoomEventProvider(RoomEventProducer roomEventProducer){
        this.roomEventProducer = roomEventProducer;
    }


    public BaseEvent provide(Room room, CrudEventType type, String author){
        return new BaseEvent(UUID.randomUUID(),
                null,
                author,
                LocalDateTime.now(),
                type,
                room.getId().toString(),
                "Room",
                room.getVersion());
    }
}
