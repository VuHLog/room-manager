package com.project1.room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantsRequest {
    private String name;

    private String phoneNumber;

    private String hometown;

    private String identityCard;

    private String vehiclePlate;
}
