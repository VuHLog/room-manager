package com.project1.room.controller;

import com.project1.room.dto.request.StoreRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.StoreResponse;
import com.project1.room.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("")
    public Page<StoreResponse> getStores(
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
        Page<StoreResponse> store = null;
        if(!search.trim().equals("")){
            store = storeService.getStoresContain(search,pageable);
        }else store = storeService.getStores(pageable);
        return store;
    }

    @GetMapping("/{storeId}")
    public ApiResponse<StoreResponse> getStoreById(@PathVariable String storeId){
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.getById(storeId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<StoreResponse> addStore(@RequestBody StoreRequest request) {
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.addStore(request))
                .build();
    }

    @PutMapping("/{storeId}")
    public ApiResponse<StoreResponse> updateTenant(@PathVariable String storeId,@RequestBody StoreRequest request) {
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.updateStore(storeId, request))
                .build();
    }

    @DeleteMapping("/{storeId}")
    public ApiResponse<String> deleteTenant(@PathVariable String storeId) {
        storeService.deleteStoreById(storeId);
        return ApiResponse.<String>builder()
                .result("Store has been deleted")
                .build();
    }
}
