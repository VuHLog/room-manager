package com.project1.room.dto.response;

import com.project1.room.entity.Rooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoicesResponse {
    private String id;

    private int amount;

    private int year;

    private int month;

    private String paymentStatus;

    private Rooms room;
}
