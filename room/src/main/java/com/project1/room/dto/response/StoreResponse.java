package com.project1.room.dto.response;

import com.project1.room.entity.Branches;
import com.project1.room.entity.Equipments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {
    private String id;

    private int quantity;

    private String status;

    private Branches branch;

    private Equipments equipment;
}
