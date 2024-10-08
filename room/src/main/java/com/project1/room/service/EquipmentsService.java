package com.project1.room.service;

import com.project1.room.dto.request.EquipmentsRequest;
import com.project1.room.dto.response.EquipmentsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipmentsService {
    Page<EquipmentsResponse> getEquipments(Pageable pageable);

    Page<EquipmentsResponse> getEquipmentsContain(String text, Pageable pageable);

    Page<EquipmentsResponse> getEquipmentsByRoomId(String roomId, Pageable pageable);

    EquipmentsResponse getById(String id);

    EquipmentsResponse addEquipment(EquipmentsRequest request);

    EquipmentsResponse updateEquipment(String equipmentId, EquipmentsRequest request);

    void deleteEquipmentById(String equipmentId);
}
