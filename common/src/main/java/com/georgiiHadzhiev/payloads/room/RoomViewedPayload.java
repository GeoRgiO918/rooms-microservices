package com.georgiiHadzhiev.payloads.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.georgiiHadzhiev.payloads.Payload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomViewedPayload  implements Payload {


    public RoomViewedPayload() {
    }
}
