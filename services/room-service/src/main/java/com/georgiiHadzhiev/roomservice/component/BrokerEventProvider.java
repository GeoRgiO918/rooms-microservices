package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.entity.CrudEventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BrokerEventProvider {



    private BrokerEventPublisher brokerEventPublisher;

    public BrokerEventProvider(BrokerEventPublisher brokerEventPublisher){
        this.brokerEventPublisher = brokerEventPublisher;
    }


    public BaseEvent provide(Room room, CrudEventType type, String author){
        return new BaseEvent(UUID.randomUUID(),
                null,
                author,
                LocalDateTime.now(),
                type,
                room.getId().toString(),
                "Room",
                room.getVersion(),
                "Room was " + type + " by " + author);
    }

    public BaseEvent provideEmpty(CrudEventType type, String author){
        return  new BaseEvent(UUID.randomUUID(),
                null,
                author,
                LocalDateTime.now(),
                type,
                null,
                "Room",
                0,
                "EMPTY");
    }
}
