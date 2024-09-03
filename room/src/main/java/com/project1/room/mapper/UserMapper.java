package com.project1.room.mapper;

import com.project1.room.dto.request.UserCreationRequest;
import com.project1.room.dto.request.UserUpdateRequest;
import com.project1.room.dto.response.UserResponse;
import com.project1.room.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "user_roles",ignore = true)
    Users toUser(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    @Mapping(target = "user_roles", ignore = true)
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}
