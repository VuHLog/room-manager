package com.project1.room.dto.response;

import com.project1.room.entity.Rooms;
import com.project1.room.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRoomsResponse {
    private String id;

    private int usage_quantity;

    private int price;

    private int year;

    private int month;

    private Rooms room;

    private Services service;
}
