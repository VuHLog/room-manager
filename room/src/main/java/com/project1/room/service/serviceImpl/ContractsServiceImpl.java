package com.project1.room.service.serviceImpl;

import com.project1.room.dao.ContractsRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.entity.Contracts;
import com.project1.room.entity.Rooms;
import com.project1.room.mapper.ContractsMapper;
import com.project1.room.service.ContractsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractsServiceImpl implements ContractsService {
    @Autowired
    private ContractsRepository contractsRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private ContractsMapper contractsMapper;

    @Override
    public Page<ContractsResponse> getContracts(Pageable pageable) {
        return contractsRepository.findAll(pageable).map(contractsMapper::toContractsResponse);
    }

    @Override
    public Page<ContractsResponse> getContractsContain(String text, Pageable pageable) {
        return contractsRepository.findByDescriptionContainsIgnoreCase(text, pageable).map(contractsMapper::toContractsResponse);
    }

    @Override
    public ContractsResponse getById(String roomId) {
        Contracts contract = contractsRepository.findById(roomId).orElse(null);
        return contractsMapper.toContractsResponse(contract);
    }

    @Override
    public ContractsResponse addContract(ContractsRequest request) {
        Contracts contract = contractsMapper.toContract(request);

        // add room to contract
        Rooms room = roomsRepository.findById(request.getRoomId()).orElseThrow(null);
        contract.setRoom(room);

        //set contract to tenant_contract
        contract.getTenantContracts().forEach(tenantContract -> tenantContract.setContract(contract));

        return contractsMapper.toContractsResponse(contractsRepository.save(contract));
    }

    @Override
    public ContractsResponse updateContract(String roomId, ContractsRequest request) {
        Contracts contract = contractsRepository.findById(roomId).orElseThrow(null);
        contractsMapper.updateContract(contract, request);

        if(!contract.getRoom().getId().equals(request.getRoomId())) {
            Rooms room = roomsRepository.findById(request.getRoomId()).orElseThrow(null);
            contract.setRoom(room);
        }

        //set contract to tenant_contract
        contract.getTenantContracts().forEach(tenantContract -> tenantContract.setContract(contract));

        return contractsMapper.toContractsResponse(contractsRepository.save(contract));
    }

    public void updateContractStatus(String contractId, String status) {
        Contracts contract = contractsRepository.findById(contractId).orElseThrow(null);
        contract.setStatus(status);
        contractsMapper.toContractsResponse(contractsRepository.save(contract));
    }

    @Override
    public void deleteContractById(String roomId) {
        updateContractStatus(roomId, "disabled");
    }
}
