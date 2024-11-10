package com.project1.room.service;

import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.entity.Contracts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractsService {
    Page<ContractsResponse> getContracts(String field, Integer pageNumber, Integer pageSize, String sort, String search, String roomId, String status);

    ContractsResponse getById(String id);

    ContractsResponse getByStatusAndRoomId(String status, String roomId);

    List<Contracts> getContractsByStatusAndRoomId(String status, String roomId);

    ContractsResponse addContract(ContractsRequest request);

    ContractsResponse updateContract(String contractId, ContractsRequest request);

    void deleteContractById(String contractId);
}
