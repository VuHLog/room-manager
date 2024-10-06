package com.project1.room.controller;

import com.project1.room.dto.request.ServicesRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.ServicesResponse;
import com.project1.room.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @GetMapping("")
    public Page<ServicesResponse> getServices(
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
        Page<ServicesResponse> services = null;
        if(!search.trim().equals("")){
            services = servicesService.getServicesContain(search,pageable);
        }else services = servicesService.getServices(pageable);
        return services;
    }

    @GetMapping("/{serviceId}")
    public ApiResponse<ServicesResponse> getServiceById(@PathVariable String serviceId){
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.getById(serviceId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<ServicesResponse> addService(@RequestBody ServicesRequest request) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.addService(request))
                .build();
    }

    @PutMapping("/{ServiceId}")
    public ApiResponse<ServicesResponse> updateService(@PathVariable String serviceId,@RequestBody ServicesRequest request) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.updateService(serviceId, request))
                .build();
    }

    @DeleteMapping("/{ServiceId}")
    public ApiResponse<String> deleteService(@PathVariable String serviceId) {
        servicesService.deleteServiceById(serviceId);
        return ApiResponse.<String>builder()
                .result("Service has been deleted")
                .build();
    }
}
