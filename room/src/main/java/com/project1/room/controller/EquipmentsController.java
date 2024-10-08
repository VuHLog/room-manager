package com.project1.room.controller;

import com.project1.room.dto.request.EquipmentsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.EquipmentsResponse;
import com.project1.room.service.EquipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipments")
public class EquipmentsController {
    @Autowired
    private EquipmentsService equipmentsService;

    @GetMapping("")
    public Page<EquipmentsResponse> getEquipments(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(name = "search", required = false, defaultValue = "") String search
    ){
        Sort sortable = null;
        if(sort.toUpperCase().equals("ASC")){
            sortable = Sort.by(field).ascending();
        }
        if(sort.toUpperCase().equals("DESC")){
            sortable = Sort.by(field).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortable);
        Page<EquipmentsResponse> equipments = null;
        if(!search.trim().equals("")){
            equipments = equipmentsService.getEquipmentsContain(search,pageable);
        }else equipments = equipmentsService.getEquipments(pageable);
        return equipments;
    }

    @GetMapping("/{equipmentId}")
    public ApiResponse<EquipmentsResponse> getEquipmentById(@PathVariable String equipmentId){
        return ApiResponse.<EquipmentsResponse>builder()
                .result(equipmentsService.getById(equipmentId))
                .build();
    }

    @GetMapping("/room/{roomId}")
    public Page<EquipmentsResponse> getEquipmentsByRoomId(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @PathVariable String roomId
    ){
        Sort sortable = null;
        if(sort.toUpperCase().equals("ASC")){
            sortable = Sort.by(field).ascending();
        }
        if(sort.toUpperCase().equals("DESC")){
            sortable = Sort.by(field).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortable);
        return equipmentsService.getEquipmentsByRoomId(roomId,pageable);
    }

    @PostMapping("")
    public ApiResponse<EquipmentsResponse> addEquipment(@RequestBody EquipmentsRequest request) {
        return ApiResponse.<EquipmentsResponse>builder()
                .result(equipmentsService.addEquipment(request))
                .build();
    }

    @PutMapping("/{equipmentId}")
    public ApiResponse<EquipmentsResponse> updateEquipment(@PathVariable String equipmentId,@RequestBody EquipmentsRequest request) {
        return ApiResponse.<EquipmentsResponse>builder()
                .result(equipmentsService.updateEquipment(equipmentId, request))
                .build();
    }

    @DeleteMapping("/{equipmentId}")
    public ApiResponse<String> deleteEquipment(@PathVariable String equipmentId) {
        equipmentsService.deleteEquipmentById(equipmentId);
        return ApiResponse.<String>builder()
                .result("Equipment has been deleted")
                .build();
    }
}
