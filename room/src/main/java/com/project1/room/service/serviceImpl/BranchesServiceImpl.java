package com.project1.room.service.serviceImpl;

import com.project1.room.dao.BranchesRepository;
import com.project1.room.dao.UsersRepository;
import com.project1.room.dto.request.BranchesRequest;
import com.project1.room.dto.response.BranchesResponse;
import com.project1.room.entity.Branches;
import com.project1.room.entity.Users;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.BranchesMapper;
import com.project1.room.service.BranchesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BranchesServiceImpl implements BranchesService {
    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private BranchesMapper branchesMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Page<BranchesResponse> getBranches(Pageable pageable) {
        return branchesRepository.findAll(pageable).map(branchesMapper::toBranchResponse);
    }

    @Override
    public Page<BranchesResponse> getBranchesContain(String text, Pageable pageable) {
        return branchesRepository.findByNameContainsIgnoreCase(text, pageable).map(branchesMapper::toBranchResponse);
    }

    @Override
    public BranchesResponse getById(String branchId) {
        Branches branch = branchesRepository.findById(branchId).orElseThrow(null);
        return branchesMapper.toBranchResponse(branch);
    }

    @Override
    public BranchesResponse addBranch(BranchesRequest request) {
        Branches branch = branchesMapper.toBranch(request);
        Users user = usersRepository.findById(request.getManagerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        branch.setManager(user);
        return branchesMapper.toBranchResponse(branchesRepository.save(branch));
    }

    @Override
    public BranchesResponse updateBranch(String branchId, BranchesRequest request) {
        Branches branch = branchesRepository.findById(branchId).orElseThrow(null);
        branchesMapper.updateBranch(branch, request);
        if(!branch.getManager().getId().equals(request.getManagerId())){
            Users user = usersRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            branch.setManager(user);
        }
        return branchesMapper.toBranchResponse(branchesRepository.save(branch));
    }

    @Override
    public void deleteBranchById(String branchId) {
        branchesRepository.deleteById(branchId);
    }
}
