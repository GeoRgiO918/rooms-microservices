package com.georgiiHadzhiev.payloads.room;

import com.georgiiHadzhiev.payloads.Payload;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomUpdatedPayload implements Payload {

    private String number;
    private Boolean numberChanged;

    private String description;
    private Boolean descriptionChanged;

    private String roomType;
    private Boolean roomTypeChanged;

    private Integer personCount;
    private Boolean personCountChanged;

    // Конструктор
    public RoomUpdatedPayload() {}

    // Геттеры и сеттеры
    public String getNumber() { return number; }
    public void setNumber(String number) {
        this.number = number;
        this.numberChanged = true;
    }

    public Boolean getNumberChanged() { return numberChanged; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
        this.descriptionChanged = true;
    }

    public Boolean getDescriptionChanged() { return descriptionChanged; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
        this.roomTypeChanged = true;
    }

    public Boolean getRoomTypeChanged() { return roomTypeChanged; }

    public Integer getPersonCount() { return personCount; }
    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
        this.personCountChanged = true;
    }

    public Boolean getPersonCountChanged() { return personCountChanged; }
}
