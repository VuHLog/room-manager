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
        Page<RoomsResponse> rooms = null;
        if(!search.trim().equals("")){
            rooms = roomsService.getRoomsContain(search,pageable);
        }else rooms = roomsService.getRooms(pageable);
        return rooms;
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomsResponse> getRoomById(@PathVariable String roomId){
        return ApiResponse.<RoomsResponse>builder()
                .result(roomsService.getById(roomId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<RoomsResponse> addRoom(@RequestBody RoomsRequest request) {
        return ApiResponse.<RoomsResponse>builder()
                .result(roomsService.addRoom(request))
                .build();
    }

    @PutMapping("/{roomId}")
    public ApiResponse<RoomsResponse> updateRoom(@PathVariable String roomId,@RequestBody RoomsRequest request) {
        return ApiResponse.<RoomsResponse>builder()
                .result(roomsService.updateRoom(roomId, request))
                .build();
    }

    @DeleteMapping("/{roomId}")
    public ApiResponse<String> deleteRoom(@PathVariable String roomId) {
        roomsService.deleteRoomById(roomId);
        return ApiResponse.<String>builder()
                .result("Room has been disabled")
                .build();
    }
}
