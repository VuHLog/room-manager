package com.project1.room.dto.response;

import com.project1.room.entity.User_Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;

    private String username;

    private String password;

    private String avatarUrl;

    private Set<User_Role> user_roles;
}
