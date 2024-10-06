package com.project1.room.dto.response;

import com.project1.room.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchesResponse {
    private String id;

    private String name;

    private String address;

    private String phoneNumber;

    private Users manager;
}
