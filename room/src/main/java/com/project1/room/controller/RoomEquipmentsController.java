package com.project1.room.controller;

import com.project1.room.dto.request.RoomEquipmentsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.RoomEquipmentsResponse;
import com.project1.room.service.RoomEquipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roomEquipments")
public class RoomEquipmentsController {
    @Autowired
    private RoomEquipmentsService roomEquipmentsService;

    @GetMapping("")
    public Page<RoomEquipmentsResponse> getRoomEquipments(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(name = "search", required = false, defaultValue = "") String search
    ){
        Sort sortable = null;
        if(sort.equalsIgnoreCase("ASC")){
            sortable = Sort.by(field).ascending();
        }
        if(sort.equalsIgnoreCase("DESC")){
            sortable = Sort.by(field).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortable);
        Page<RoomEquipmentsResponse> RoomEquipments = null;
        if(!search.trim().isEmpty()){
            RoomEquipments = roomEquipmentsService.getRoomEquipmentsContain(search,pageable);
        }else RoomEquipments = roomEquipmentsService.getRoomEquipments(pageable);
        return RoomEquipments;
    }

    @GetMapping("/{roomEquipmentId}")
    public ApiResponse<RoomEquipmentsResponse> getById(@PathVariable String roomEquipmentId){
        return ApiResponse.<RoomEquipmentsResponse>builder()
                .result(roomEquipmentsService.getById(roomEquipmentId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<RoomEquipmentsResponse> addRoomEquipment(@RequestBody RoomEquipmentsRequest request) {
        return ApiResponse.<RoomEquipmentsResponse>builder()
                .result(roomEquipmentsService.addRoomEquipment(request))
                .build();
    }

    @PutMapping("/{roomEquipmentId}")
    public ApiResponse<RoomEquipmentsResponse> updateRoomEquipment(@PathVariable String roomEquipmentId,@RequestBody RoomEquipmentsRequest request) {
        return ApiResponse.<RoomEquipmentsResponse>builder()
                .result(roomEquipmentsService.updateRoomEquipment(roomEquipmentId, request))
                .build();
    }

    @DeleteMapping("/{roomEquipmentId}")
    public ApiResponse<String> deleteRoomEquipment(@PathVariable String roomEquipmentId) {
        roomEquipmentsService.deleteRoomEquipmentById(roomEquipmentId);
        return ApiResponse.<String>builder()
                .result("Room Equipment has been disabled")
                .build();
    }
}
