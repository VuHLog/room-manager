package com.project1.room.dto.response;

import com.project1.room.entity.RoomEquipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentsResponse {
    private String id;

    private String name;

    private List<RoomEquipment> roomEquipments;
}
