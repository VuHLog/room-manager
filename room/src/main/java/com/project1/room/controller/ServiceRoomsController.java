package com.project1.room.controller;

import com.project1.room.dto.request.ServiceRoomsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.ServiceRoomsResponse;
import com.project1.room.service.ServiceRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceRooms")
public class ServiceRoomsController {
    @Autowired
    private ServiceRoomsService serviceRoomsService;

    @GetMapping("")
    public Page<ServiceRoomsResponse> getServiceRooms(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "roomId", required = false, defaultValue = "") String roomId
    ){
        return serviceRoomsService.getServiceRooms(field, pageNumber, pageSize, sort, search, roomId);
    }

    @GetMapping("/{serviceRoomId}")
    public ApiResponse<ServiceRoomsResponse> getServiceRoomById(@PathVariable String serviceRoomId){
        return ApiResponse.<ServiceRoomsResponse>builder()
                .result(serviceRoomsService.getById(serviceRoomId))
                .build();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @serviceRoomsServiceImpl.isCreateForManager(#request.roomId))")
    public ApiResponse<ServiceRoomsResponse> addServiceRoom(@RequestBody ServiceRoomsRequest request) {
        return ApiResponse.<ServiceRoomsResponse>builder()
                .result(serviceRoomsService.addServiceRoom(request))
                .build();
    }

    @PutMapping("/{serviceRoomId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @serviceRoomsServiceImpl.hasManager(#serviceRoomId))")
    public ApiResponse<ServiceRoomsResponse> updateServiceRoom(@PathVariable String serviceRoomId,@RequestBody ServiceRoomsRequest request) {
        return ApiResponse.<ServiceRoomsResponse>builder()
                .result(serviceRoomsService.updateServiceRoom(serviceRoomId, request))
                .build();
    }

    @DeleteMapping("/{serviceRoomId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @serviceRoomsServiceImpl.hasManager(#serviceRoomId))")
    public ApiResponse<String> deleteServiceRoom(@PathVariable String serviceRoomId) {
        serviceRoomsService.deleteServiceRoomsById(serviceRoomId);
        return ApiResponse.<String>builder()
                .result("Service Room has been disabled")
                .build();
    }
}
