package com.project1.room.dto.request;

import com.project1.room.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    private String password;

    private String avatarUrl;

    private Set<Role> roles;
}
