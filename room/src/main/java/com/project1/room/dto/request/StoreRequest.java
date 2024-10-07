package com.project1.room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRequest {
    private int quantity;

    private String status;

    private String branchId;

    private String equipmentId;
}
