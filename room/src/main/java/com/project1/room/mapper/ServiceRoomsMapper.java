package com.project1.room.mapper;

import com.project1.room.dto.request.ServiceRoomsRequest;
import com.project1.room.dto.response.ServiceRoomsResponse;
import com.project1.room.entity.ServiceRooms;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceRoomsMapper {
    @Mapping(target = "service",ignore = true)
    @Mapping(target = "room",ignore = true)
    ServiceRooms toServiceRoom(ServiceRoomsRequest request);

    ServiceRoomsResponse toServiceRoomsResponse(ServiceRooms serviceRoom);

    @Mapping(target = "service",ignore = true)
    @Mapping(target = "room",ignore = true)
    void updateServiceRoom(@MappingTarget ServiceRooms serviceRoom, ServiceRoomsRequest request);
}
