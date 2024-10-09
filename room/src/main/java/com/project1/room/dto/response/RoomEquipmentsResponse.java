package com.project1.room.dto.response;

import com.project1.room.entity.Equipments;
import com.project1.room.entity.Rooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomEquipmentsResponse {
    private String id;

    private String status;

    private int quantity;

    private Rooms room;

    private Equipments equipment;
}
