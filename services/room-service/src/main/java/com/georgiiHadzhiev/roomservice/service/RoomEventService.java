package com.georgiiHadzhiev.roomservice.service;

import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.RoomEvent;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoomEventService {



    private RoomEventProducer roomEventProducer;

    public RoomEventService(RoomEventProducer roomEventProducer){
        this.roomEventProducer = roomEventProducer;
    }


    public void roomDeleted(Room room){
        RoomEvent roomEvent = new RoomEvent(room.getId(),"user", LocalDateTime.now(), EventType.DELETED);
        roomEventProducer.sendRoomEvent(roomEvent);
    }
}
