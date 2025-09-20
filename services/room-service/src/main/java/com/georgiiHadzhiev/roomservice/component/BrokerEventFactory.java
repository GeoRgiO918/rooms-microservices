package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.Payload;
import com.georgiiHadzhiev.payloads.room.RoomCreatedPayload;
import com.georgiiHadzhiev.payloads.room.RoomDeletedPayload;
import com.georgiiHadzhiev.payloads.room.RoomUpdatedPayload;
import com.georgiiHadzhiev.payloads.room.RoomViewedPayload;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BrokerEventFactory {

    private final RoomPayloadFactory payloadFactory;

    public BrokerEventFactory(RoomPayloadFactory payloadFactory) {
        this.payloadFactory = payloadFactory;
    }

    public BaseEvent<RoomCreatedPayload> createRoomCreated(Room room) {

        RoomCreatedPayload payload = payloadFactory.createRoomCreatedPayload(room);
        return new BaseEvent<RoomCreatedPayload>(UUID.randomUUID(),
                null,
                "room-service",
                Instant.now(),
                room.getId().toString(),
                "Room",
                room.getVersion(),
                "Room was created " + " by room-service",
                EventType.ROOM_CREATED,
                payload,
                "USER SERVICE WORK IN PROGRESS");
    }

    public BaseEvent<RoomUpdatedPayload> createRoomUpdated(Room oldRoom, Room newRoom) {

        RoomUpdatedPayload payload = payloadFactory.createRoomUpdatedPayload(oldRoom, newRoom);
        return new BaseEvent<RoomUpdatedPayload>(UUID.randomUUID(),
                null,
                "room-service",
                Instant.now(),
                newRoom.getId().toString(),
                "Room",
                newRoom.getVersion(),
                "Room was updated " + " by room-service",
                EventType.ROOM_UPDATED,
                payload,
                "USER SERVICE WORK IN PROGRESS");

    }

    public BaseEvent<RoomDeletedPayload> createRoomDeleted(Room room) {
        RoomDeletedPayload payload = new RoomDeletedPayload();
        return new BaseEvent<RoomDeletedPayload>(UUID.randomUUID(),
                null,
                "room-service",
                Instant.now(),
                room.getId().toString(),
                "Room",
                room.getVersion(),
                "Room " + room.getId().toString() + " was deleted",
                EventType.ROOM_VIEWED,
                payload,
                "USER SERVICE WORK IN PROGRESS");
    }

    public BaseEvent<RoomViewedPayload> createRoomViewed(Room room){
        return new BaseEvent<RoomViewedPayload>(UUID.randomUUID(),
                null,
                "room-service",
                Instant.now(),
                room.getId().toString(),
                "Room",
                room.getVersion(),
                "Room " + room.getId().toString() + " was viewed",
                EventType.ROOM_VIEWED,
                new RoomViewedPayload(),
                "USER SERVICE WORK IN PROGRESS");
    }

    public BaseEvent<RoomViewedPayload> createAllRoomViewed() {
        RoomViewedPayload payload = new RoomViewedPayload();
        return new BaseEvent<RoomViewedPayload>(UUID.randomUUID(),
                null,
                "room-service",
                Instant.now(),
                null,
                "Room",
                0,
                "EMPTY",
                EventType.ROOM_VIEWED,
                payload,
                "USER SERVICE WORK IN PROGRESS");
    }


}
