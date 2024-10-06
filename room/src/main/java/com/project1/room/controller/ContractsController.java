package com.project1.room.controller;

import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.service.ContractsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class ContractsController {
    @Autowired
    private ContractsService contractsService;

    @GetMapping("")
    public Page<ContractsResponse> getContracts(
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
        Page<ContractsResponse> contracts = null;
        if(!search.trim().equals("")){
            contracts = contractsService.getContractsContain(search,pageable);
        }else contracts = contractsService.getContracts(pageable);
        return contracts;
    }

    @GetMapping("/{contractId}")
    public ApiResponse<ContractsResponse> getContractById(@PathVariable String contractId){
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.getById(contractId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<ContractsResponse> addContract(@RequestBody ContractsRequest request) {
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.addContract(request))
                .build();
    }

    @PutMapping("/{contractId}")
    public ApiResponse<ContractsResponse> updateContract(@PathVariable String contractId,@RequestBody ContractsRequest request) {
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.updateContract(contractId, request))
                .build();
    }

    @DeleteMapping("/{contractId}")
    public ApiResponse<String> deleteContract(@PathVariable String contractId) {
        contractsService.deleteContractById(contractId);
        return ApiResponse.<String>builder()
                .result("Contract has been disabled")
                .build();
    }
}
