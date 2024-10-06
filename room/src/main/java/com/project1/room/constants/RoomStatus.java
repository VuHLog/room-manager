package com.project1.room.constants;

import lombok.Getter;

@Getter
public enum RoomStatus {
    EMPTY("empty"),
    USED("used"),
    DISABLED("disabled");
    RoomStatus(String status) {
        this.status = status;
    }

    private final String status;
}
