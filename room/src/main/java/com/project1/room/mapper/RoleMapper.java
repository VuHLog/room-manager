package com.project1.room.mapper;

import com.project1.room.dto.response.RoleResponse;
import com.project1.room.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toRoleResponse(Role role);
}
