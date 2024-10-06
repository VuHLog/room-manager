package com.project1.room.mapper;

import com.project1.room.dto.request.EquipmentsRequest;
import com.project1.room.dto.response.EquipmentsResponse;
import com.project1.room.entity.Equipments;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EquipmentsMapper {
    Equipments toEquipment(EquipmentsRequest request);

    EquipmentsResponse toEquipmentsResponse(Equipments equipment);

    void updateEquipment(@MappingTarget Equipments equipment, EquipmentsRequest request);
}
