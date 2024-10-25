package com.project1.room.constants;

import lombok.Getter;

@Getter
public enum ContractStatus {
    ENABLED("enabled"),
    DISABLED("disabled");
    ContractStatus(String status) {
        this.status = status;
    }

    private final String status;
}
