package com.project1.room.mapper;

import com.project1.room.dto.request.BranchesRequest;
import com.project1.room.dto.response.BranchesResponse;
import com.project1.room.entity.Branches;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchesMapper {
    @Mapping(target = "manager",ignore = true)
    Branches toBranch(BranchesRequest request);

    BranchesResponse toBranchResponse(Branches branches);

    @Mapping(target = "manager",ignore = true)
    void updateBranch(@MappingTarget Branches branch, BranchesRequest request);
}
