package com.project1.room.service;

import com.project1.room.dto.request.UserChangePasswordRequest;
import com.project1.room.dto.request.UserCreationRequest;
import com.project1.room.dto.request.UserUpdateRequest;
import com.project1.room.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponse> getUsers(Pageable pageable);

    Page<UserResponse> getUsersContains(String text,Pageable pageable);
    UserResponse getById(String id);

    UserResponse getByUsername(String username);

    UserResponse addUser(UserCreationRequest request);

    UserResponse updateUser(String userId, UserUpdateRequest request);

    UserResponse changePassword(String userId, UserChangePasswordRequest request);

    void deleteUser(String userId);

    UserResponse getMyInfo();

}
