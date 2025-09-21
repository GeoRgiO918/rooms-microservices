package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.components.AbstractPayloadFactory;
import com.georgiiHadzhiev.payloads.roomservice.RoomCreatedPayload;
import com.georgiiHadzhiev.payloads.roomservice.RoomUpdatedPayload;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Component
public class RoomPayloadFactory extends AbstractPayloadFactory {



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

}
