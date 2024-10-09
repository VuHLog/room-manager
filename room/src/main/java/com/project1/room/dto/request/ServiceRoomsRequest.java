package com.project1.room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRoomsRequest {
    private int usage_quantity;

    private int price;

    private int year;

    private int month;

    private String roomId;

    private String serviceId;
}
