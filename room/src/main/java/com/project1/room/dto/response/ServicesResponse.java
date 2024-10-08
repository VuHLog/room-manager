package com.project1.room.dto.response;

import com.project1.room.entity.ServiceRooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicesResponse {
    private String id;

    private String name;

    private List<ServiceRooms> serviceRooms;
}
