package com.project1.room.controller;

import com.project1.room.dto.request.TenantsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.TenantsResponse;
import com.project1.room.service.TenantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenants")
public class TenantsController {
    @Autowired
    private TenantsService tenantsService;

    @GetMapping("")
    public Page<TenantsResponse> getTenants(
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
        Page<TenantsResponse> tenants = null;
        if(!search.trim().equals("")){
            tenants = tenantsService.getTenantsContain(search,pageable);
        }else tenants = tenantsService.getTenants(pageable);
        return tenants;
    }

    @GetMapping("/{tenantId}")
    public ApiResponse<TenantsResponse> getTenantById(@PathVariable String tenantId){
        return ApiResponse.<TenantsResponse>builder()
                .result(tenantsService.getById(tenantId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<TenantsResponse> addTenant(@RequestBody TenantsRequest request) {
        return ApiResponse.<TenantsResponse>builder()
                .result(tenantsService.addTenant(request))
                .build();
    }

    @PutMapping("/{tenantId}")
    public ApiResponse<TenantsResponse> updateTenant(@PathVariable String tenantId,@RequestBody TenantsRequest request) {
        return ApiResponse.<TenantsResponse>builder()
                .result(tenantsService.updateTenant(tenantId, request))
                .build();
    }

    @DeleteMapping("/{tenantId}")
    public ApiResponse<String> deleteTenant(@PathVariable String tenantId) {
        tenantsService.deleteTenantById(tenantId);
        return ApiResponse.<String>builder()
                .result("Tenant has been deleted")
                .build();
    }
}
