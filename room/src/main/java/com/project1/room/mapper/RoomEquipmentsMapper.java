package com.project1.room.mapper;

import com.project1.room.dto.request.RoomEquipmentsRequest;
import com.project1.room.dto.response.RoomEquipmentsResponse;
import com.project1.room.entity.RoomEquipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomEquipmentsMapper {
    @Mapping(target = "equipment",ignore = true)
    @Mapping(target = "room",ignore = true)
    RoomEquipment toRoomEquipment(RoomEquipmentsRequest request);

    RoomEquipmentsResponse toRoomEquipmentsResponse(RoomEquipment roomEquipment);

    @Mapping(target = "equipment",ignore = true)
    @Mapping(target = "room",ignore = true)
    void updateRoomEquipment(@MappingTarget RoomEquipment roomEquipment, RoomEquipmentsRequest request);
}
