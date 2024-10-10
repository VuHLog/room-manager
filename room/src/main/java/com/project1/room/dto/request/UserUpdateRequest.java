package com.project1.room.dto.request;

import com.project1.room.entity.Role;
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
public class UserUpdateRequest {
    private String password;

    private String avatarUrl;

    private String phoneNumber;

    private List<UserRole> userRoles;
}
