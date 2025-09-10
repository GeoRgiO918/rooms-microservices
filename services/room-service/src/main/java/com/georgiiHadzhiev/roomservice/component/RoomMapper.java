package com.georgiiHadzhiev.roomservice.component;

import com.georgiiHadzhiev.roomservice.dto.RoomDto;
import com.georgiiHadzhiev.roomservice.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room toEntity(RoomDto dto){

        Room room = new Room();
        room.setVersion(dto.getVersion());
        room.setDescription(dto.getDescription());
        room.setType(dto.getType());
        room.setNumber(dto.getNumber());
        room.setPersonCount(dto.getPersonCount());
        room.setId(dto.getId());

        return room;
    }

    public RoomDto toDto(Room room){
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setVersion(room.getVersion());
        dto.setDescription(room.getDescription());
        dto.setType(room.getType());
        dto.setNumber(room.getNumber());
        dto.setPersonCount(room.getPersonCount());
        return dto;
    }
}
