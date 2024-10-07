package com.project1.room.mapper;

import com.project1.room.dto.request.StoreRequest;
import com.project1.room.dto.response.StoreResponse;
import com.project1.room.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(target = "branch",ignore = true)
    @Mapping(target = "equipment",ignore = true)
    Store toStore(StoreRequest request);

    StoreResponse toStoreResponse(Store store);

    @Mapping(target = "branch",ignore = true)
    @Mapping(target = "equipment",ignore = true)
    void updateStore(@MappingTarget Store store, StoreRequest request);
}
