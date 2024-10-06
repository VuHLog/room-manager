package com.project1.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantsResponse {
    private String id;

    private String name;

    private String phoneNumber;

    private String hometown;

    private String identityCard;

    private String vehiclePlate;
}
