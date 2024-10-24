package com.project1.room.service;

import com.project1.room.dto.request.BranchesRequest;
import com.project1.room.dto.response.BranchesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BranchesService {
    Page<BranchesResponse> getBranches(Pageable pageable);

    Page<BranchesResponse> getBranchesContain(String text, String managerId, Pageable pageable);

    BranchesResponse getById(String id);

    BranchesResponse addBranch(BranchesRequest request);

    BranchesResponse updateBranch(String branchId, BranchesRequest request);

    void deleteBranchById(String branchId);
}
