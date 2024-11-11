package com.project1.room.controller;

import com.project1.room.dto.request.RoomsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.RoomsResponse;
import com.project1.room.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    @GetMapping("")
    public Page<RoomsResponse> getRooms(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(name = "search", required = false, defaultValue = "") String searchText,
            @RequestParam(name = "branchId", required = false, defaultValue = "") String branchId,
            @RequestParam(name = "managerId", required = false, defaultValue = "") String managerId,
            @RequestParam(name = "status", required = false, defaultValue = "") String status,
            @RequestParam(name = "capacity", required = false, defaultValue = "") Integer capacity
    ){
        return roomsService.getRooms(field, pageNumber, pageSize, sort, searchText, branchId, managerId, status, capacity);
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomsResponse> getRoomById(@PathVariable String roomId){
        return ApiResponse.<RoomsResponse>builder()
                .result(roomsService.getById(roomId))
                .build();
    }

    @GetMapping("/branch/{branchId}")
    public Page<RoomsResponse> getRoomsByBranchId(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @PathVariable String branchId
    ){
        Sort sortable = null;
        if(sort.equalsIgnoreCase("ASC")){
            sortable = Sort.by(field).ascending();
        }
        if(sort.equalsIgnoreCase("DESC")){
            sortable = Sort.by(field).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortable);
        return roomsService.getRoomsByBranchId(branchId, pageable);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<RoomsResponse> addRoom(@RequestBody RoomsRequest request) {
        return ApiResponse.<RoomsResponse>builder()
                .result(roomsService.addRoom(request))
                .build();
    }

    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @roomsServiceImpl.hasManager(#roomId))")
    public ApiResponse<RoomsResponse> updateRoom(@PathVariable String roomId,@RequestBody RoomsRequest request) {
        return ApiResponse.<RoomsResponse>builder()
                .result(roomsService.updateRoom(roomId, request))
                .build();
    }

    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteRoom(@PathVariable String roomId) {
        roomsService.deleteRoomById(roomId);
        return ApiResponse.<String>builder()
                .result("Room has been disabled")
                .build();
    }
}
