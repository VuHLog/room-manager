package com.project1.room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomEquipmentsRequest {
    private String status;

    private int quantity;

    private String roomId;

    private String equipmentId;
}
