package com.project1.room.service;

import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ContractsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractsService {
    Page<ContractsResponse> getContracts(Pageable pageable);

    Page<ContractsResponse> getContractsContain(String text, Pageable pageable);

    ContractsResponse getById(String id);

    ContractsResponse addContract(ContractsRequest request);

    ContractsResponse updateContract(String contractId, ContractsRequest request);

    void deleteContractById(String contractId);
}
