package com.project1.room.service;

import com.project1.room.dto.request.RoomsRequest;
import com.project1.room.dto.response.RoomsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomsService {
    Page<RoomsResponse> getRooms(String field, Integer pageNumber, Integer pageSize, String sort, String searchText, String branchId, String managerId, String status, Integer capacity);

    Page<RoomsResponse> getRoomsByBranchId(String branchId, Pageable pageable);

    RoomsResponse getById(String id);

    RoomsResponse addRoom(RoomsRequest request);

    RoomsResponse updateRoom(String roomId, RoomsRequest request);

    void deleteRoomById(String roomId);
}
