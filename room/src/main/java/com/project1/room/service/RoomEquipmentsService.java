package com.project1.room.service;

import com.project1.room.dto.request.RoomEquipmentsRequest;
import com.project1.room.dto.response.RoomEquipmentsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomEquipmentsService {
    Page<RoomEquipmentsResponse> getRoomEquipments(String field, Integer pageNumber, Integer pageSize, String sort, String search, String roomId);

    Page<RoomEquipmentsResponse> getRoomEquipmentsContain(String text, Pageable pageable);

    RoomEquipmentsResponse getById(String id);

    RoomEquipmentsResponse addRoomEquipment(RoomEquipmentsRequest request);

    RoomEquipmentsResponse updateRoomEquipment(String roomEquipmentId, RoomEquipmentsRequest request);

    void deleteRoomEquipmentById(String roomEquipmentId);
}
