package com.project1.room.dto.request;

import com.project1.room.entity.RoomEquipment;
import com.project1.room.entity.ServiceRooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomsRequest {
    private String roomNumber;

    private int capacity;

    private String description;

    private int price;

    private String branchId;

    private List<ServiceRooms> serviceRooms;

    private List<RoomEquipment> roomEquipments;
}
