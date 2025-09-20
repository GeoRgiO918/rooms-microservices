package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.exceptions.UnsupportedEventType;
import com.georgiiHadzhiev.payloads.Payload;
import com.georgiiHadzhiev.payloads.room.RoomCreatedPayload;
import com.georgiiHadzhiev.payloads.room.RoomUpdatedPayload;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Component
public class RoomPayloadFactory {



    public RoomCreatedPayload createRoomCreatedPayload(Room room){
       return new RoomCreatedPayload(room.getNumber(),
               room.getDescription(),
               room.getType().name(),
               room.getPersonCount());
    }

    public RoomUpdatedPayload createRoomUpdatedPayload(Room oldRoom, Room newRoom){
        RoomUpdatedPayload payload = new RoomUpdatedPayload();

        setIfChanged(oldRoom.getNumber(), newRoom.getNumber(), payload::setNumber);
        setIfChanged(oldRoom.getDescription(), newRoom.getDescription(), payload::setDescription);
        setIfChanged(oldRoom.getType().name(), newRoom.getType().name(), payload::setRoomType);
        setIfChanged(oldRoom.getPersonCount(), newRoom.getPersonCount(), payload::setPersonCount);
        return payload;
    }

    private <T> void setIfChanged(T oldValue, T newValue, Consumer<T> setter) {
        if (!Objects.equals(oldValue, newValue)) {
            setter.accept(newValue);
        }
    }
}
