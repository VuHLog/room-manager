package com.project1.room.mapper;

import com.project1.room.dto.request.RoomsRequest;
import com.project1.room.dto.response.RoomsResponse;
import com.project1.room.entity.Rooms;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomsMapper {
    @Mapping(target = "branch", ignore = true)
    Rooms toRooms(RoomsRequest request);

    RoomsResponse toRoomsResponse(Rooms rooms);

    @Mapping(target = "branch", ignore = true)
    void updateRoom(@MappingTarget Rooms rooms,RoomsRequest request);
}
