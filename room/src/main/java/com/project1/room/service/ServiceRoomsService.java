package com.project1.room.service;

import com.project1.room.dto.request.ServiceRoomsRequest;
import com.project1.room.dto.response.ServiceRoomsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceRoomsService {
    Page<ServiceRoomsResponse> getServiceRooms(String field, Integer pageNumber, Integer pageSize, String sort, String search, String roomId);

    Page<ServiceRoomsResponse> getServiceRoomsContain(String text, Pageable pageable);

    ServiceRoomsResponse getById(String id);

    ServiceRoomsResponse addServiceRoom(ServiceRoomsRequest request);

    ServiceRoomsResponse updateServiceRoom(String serviceRoomId, ServiceRoomsRequest request);

    void deleteServiceRoomsById(String serviceRoomId);
}
