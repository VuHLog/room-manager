package com.project1.room.controller;

import com.project1.room.dto.request.BranchesRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.BranchesResponse;
import com.project1.room.service.BranchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branches")
public class BranchesController {
    @Autowired
    private BranchesService branchesService;

    @GetMapping("")
    public Page<BranchesResponse> getBranches(
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
        Page<BranchesResponse> branches = null;
        if(!search.trim().equals("")){
            branches = branchesService.getBranchesContain(search,pageable);
        }else branches = branchesService.getBranches(pageable);
        return branches;
    }

    @GetMapping("/{branchId}")
    public ApiResponse<BranchesResponse> getBranchById(@PathVariable String branchId){
        return ApiResponse.<BranchesResponse>builder()
                .result(branchesService.getById(branchId))
                .build();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BranchesResponse> addBranch(@RequestBody BranchesRequest request) {
        return ApiResponse.<BranchesResponse>builder()
                .result(branchesService.addBranch(request))
                .build();
    }

    @PutMapping("/{branchId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @branchesServiceImpl.hasManager(#branchId))")
    public ApiResponse<BranchesResponse> updateBranch(@PathVariable String branchId,@RequestBody BranchesRequest request) {
        return ApiResponse.<BranchesResponse>builder()
                .result(branchesService.updateBranch(branchId, request))
                .build();
    }

    @DeleteMapping("/{branchId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteBranch(@PathVariable String branchId) {
        branchesService.deleteBranchById(branchId);
        return ApiResponse.<String>builder()
                .result("Branch has been deleted")
                .build();
    }
}
