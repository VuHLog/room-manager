package com.project1.room.service;

import com.project1.room.dto.request.UserCreationRequest;
import com.project1.room.dto.request.UserUpdateRequest;
import com.project1.room.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    public Page<UserResponse> getUsers(Pageable pageable);

    public Page<UserResponse> getUsersContains(String s,Pageable pageable);
    public UserResponse getById(String id);

    public UserResponse getByUsername(String username);

    public UserResponse addUser(UserCreationRequest request);

    public UserResponse updateUser(String userId, UserUpdateRequest request);

    public void deleteUser(String userId);

    public UserResponse getMyInfo();
}
