package com.project1.room.dto.response;

import com.project1.room.entity.Rooms;
import com.project1.room.entity.TenantContract;
import com.project1.room.entity.Tenants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractsResponse {
    private String id;

    private String startDate;

    private String endDate;

    private int price;

    private int depositAmount;

    private String description;

    private String status;

    private List<TenantContract> tenantContracts;

    private Rooms room;
}
