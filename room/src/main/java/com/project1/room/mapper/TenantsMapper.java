package com.project1.room.mapper;

import com.project1.room.dto.request.TenantsRequest;
import com.project1.room.dto.response.TenantsResponse;
import com.project1.room.entity.Tenants;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TenantsMapper {
    Tenants toTenant(TenantsRequest request);

    TenantsResponse toTenantsResponse(Tenants Tenant);

    void updateTenant(@MappingTarget Tenants Tenant, TenantsRequest request);
}
