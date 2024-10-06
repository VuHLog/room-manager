package com.project1.room.mapper;

import com.project1.room.dto.request.ServicesRequest;
import com.project1.room.dto.response.ServicesResponse;
import com.project1.room.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicesMapper {
    Services toService(ServicesRequest request);

    ServicesResponse toServicesResponse(Services service);

    void updateService(@MappingTarget Services service, ServicesRequest request);
}
