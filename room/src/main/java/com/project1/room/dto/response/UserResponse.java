package com.project1.room.dto.response;

import com.project1.room.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;

    private String username;

    private String password;

    private String name;

    private String phoneNumber;

    private String avatarUrl;

    private List<UserRole> userRoles;
} 
