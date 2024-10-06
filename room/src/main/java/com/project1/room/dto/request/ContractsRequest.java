package com.project1.room.dto.request;

import com.project1.room.entity.TenantContract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractsRequest {
    private String endDate;

    private int price;

    private int depositAmount;

    private String description;

    private String status;

    private List<TenantContract> tenantContracts;

    private String roomId;
}
