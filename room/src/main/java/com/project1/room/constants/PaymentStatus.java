package com.project1.room.constants;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAID("paid"),
    CANCELED("canceled"),
    UNPAID("unpaid");
    PaymentStatus(String status) {
        this.status = status;
    }

    private final String status;
}
