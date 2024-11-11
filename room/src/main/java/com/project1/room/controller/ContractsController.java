package com.project1.room.controller;

import com.project1.room.constants.ContractStatus;
import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.service.ContractsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "roomId", required = false, defaultValue = "") String roomId,
            @RequestParam(name = "status", required = false, defaultValue = "") String status
    ){
        return contractsService.getContracts(field, pageNumber, pageSize, sort, search, roomId, status);
    }

    @GetMapping("/{contractId}")
    public ApiResponse<ContractsResponse> getContractById(@PathVariable String contractId){
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.getById(contractId))
                .build();
    }

    @GetMapping("/enabled")
    public ApiResponse<ContractsResponse> getContractsEnabledByRoomId(@RequestParam(name = "roomId", required = true) String roomId){
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.getByStatusAndRoomId(ContractStatus.ENABLED.getStatus(),roomId))
                .build();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @contractsServiceImpl.isCreateForManager(#request.roomId))")
    public ApiResponse<ContractsResponse> addContract(@RequestBody ContractsRequest request) {
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.addContract(request))
                .build();
    }

    @PutMapping("/{contractId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @contractsServiceImpl.hasManager(#contractId))")
    public ApiResponse<ContractsResponse> updateContract(@PathVariable String contractId,@RequestBody ContractsRequest request) {
        return ApiResponse.<ContractsResponse>builder()
                .result(contractsService.updateContract(contractId, request))
                .build();
    }

    @PutMapping("/auto-update-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> autoUpdateStatus() {
        contractsService.autoUpdateContractStatus();
        return ApiResponse.<String>builder()
                .result("Update contract status successfully")
                .build();
    }

    @DeleteMapping("/{contractId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @contractsServiceImpl.hasManager(#contractId))")
    public ApiResponse<String> deleteContract(@PathVariable String contractId) {
        contractsService.deleteContractById(contractId);
        return ApiResponse.<String>builder()
                .result("Contract has been disabled")
                .build();
    }
}
