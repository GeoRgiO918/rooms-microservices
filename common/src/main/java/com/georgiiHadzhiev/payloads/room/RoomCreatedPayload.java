package com.georgiiHadzhiev.payloads.room;

import com.georgiiHadzhiev.payloads.Payload;

public class RoomCreatedPayload  implements Payload {

    private String number;
    private String description;
    private String roomType;
    private int personCount;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public RoomCreatedPayload(String number, String description, String roomType, int personCount) {
        this.number = number;
        this.description = description;
        this.roomType = roomType;
        this.personCount = personCount;
    }

    public RoomCreatedPayload() {
    }
}
