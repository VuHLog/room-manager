package com.project1.room.dto.response;

import com.project1.room.entity.Branches;
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
public class RoomsResponse {
    private String id;

    private String roomNumber;

    private int capacity;

    private String description;

    private String status;

    private int price;

    private Branches branch;

    private List<ServiceRooms> serviceRooms;

    private List<RoomEquipment> roomEquipments;
}
