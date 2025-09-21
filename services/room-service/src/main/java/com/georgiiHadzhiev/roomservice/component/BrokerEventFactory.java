package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.components.AbstractEventFactory;
import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.Payload;
import com.georgiiHadzhiev.payloads.roomservice.*;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class BrokerEventFactory  extends AbstractEventFactory {

    private final RoomPayloadFactory payloadFactory;

    public BrokerEventFactory(RoomPayloadFactory payloadFactory) {
        super("room-service","Room","room.events");
        this.payloadFactory = payloadFactory;
    }



    public BaseEvent<RoomCreatedPayload> createRoomCreated(Room room) {
        RoomCreatedPayload payload = payloadFactory.createRoomCreatedPayload(room);
        return buildEvent(
                null,
                room.getId().toString(),
                room.getVersion(),
                "Room was created by room-service",
                EventType.ROOM_CREATED,
                payload
        );
    }

    public BaseEvent<RoomUpdatedPayload> createRoomUpdated(Room oldRoom, Room newRoom) {
        RoomUpdatedPayload payload = payloadFactory.createRoomUpdatedPayload(oldRoom, newRoom);
        return buildEvent(
                null,
                newRoom.getId().toString(),
                newRoom.getVersion(),
                "Room was updated by room-service",
                EventType.ROOM_UPDATED,
                payload
        );
    }

    public BaseEvent<RoomDeletedPayload> createRoomDeleted(Room room) {
        RoomDeletedPayload payload = new RoomDeletedPayload();
        return buildEvent(
                null,
                room.getId().toString(),
                room.getVersion(),
                "Room " + room.getId() + " was deleted",
                EventType.ROOM_DELETED,
                payload
        );
    }

    public BaseEvent<RoomViewedPayload> createRoomViewed(Room room) {
        return buildEvent(
                null,
                room.getId().toString(),
                room.getVersion(),
                "Room " + room.getId() + " was viewed",
                EventType.ROOM_VIEWED,
                new RoomViewedPayload()
        );
    }

    public BaseEvent<RoomViewedPayload> createAllRoomViewed() {
        return buildEvent(
                null,
                null,
                0,
                "All rooms fetched",
                EventType.ROOM_VIEWED,
                new RoomViewedPayload()
        );
    }
}
