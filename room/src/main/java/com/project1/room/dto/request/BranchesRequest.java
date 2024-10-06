package com.project1.room.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project1.room.entity.Rooms;
import com.project1.room.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchesRequest {
    private String name;

    private String address;

    private String phoneNumber;

    private String managerId;
}
