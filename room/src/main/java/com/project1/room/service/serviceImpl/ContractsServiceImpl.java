package com.project1.room.service.serviceImpl;

import com.project1.room.constants.RoomStatus;
import com.project1.room.dao.ContractsRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.specifications.ContractsSpecification;
import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.entity.Contracts;
import com.project1.room.entity.Rooms;
import com.project1.room.mapper.ContractsMapper;
import com.project1.room.service.ContractsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ContractsServiceImpl implements ContractsService {
    private final ContractsRepository contractsRepository;

    private final RoomsRepository roomsRepository;

    private final ContractsMapper contractsMapper;

    public ContractsServiceImpl(ContractsRepository contractsRepository, RoomsRepository roomsRepository, ContractsMapper contractsMapper) {
        this.contractsRepository = contractsRepository;
        this.roomsRepository = roomsRepository;
        this.contractsMapper = contractsMapper;
    }

    @Override
    public Page<ContractsResponse> getContracts(String field, Integer pageNumber, Integer pageSize, String sort, String search, String roomId, String status) {
        Specification<Contracts> specs = Specification.where(null);

        if(!roomId.trim().isEmpty()){
            specs = specs.and(ContractsSpecification.equalRoomId(roomId));
        }

        if(!status.trim().isEmpty()){
            specs = specs.and(ContractsSpecification.equalStatus(status));
        }

        Sort sortable = sort.equalsIgnoreCase("ASC") ? Sort.by(field).ascending() : Sort.by(field).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortable);
        return contractsRepository.findAll(specs, pageable).map(contractsMapper::toContractsResponse);
    }

    @Override
    public ContractsResponse getById(String roomId) {
        Contracts contract = contractsRepository.findById(roomId).orElse(null);
        return contractsMapper.toContractsResponse(contract);
    }

    @Override
    public ContractsResponse getByStatusAndRoomId(String status, String roomId) {
        return contractsMapper.toContractsResponse(contractsRepository.findByStatusAndRoom_Id(status, roomId));
    }

    @Override
    public ContractsResponse addContract(ContractsRequest request) {
        Contracts contract = contractsMapper.toContract(request);

        // add room to contract
        Rooms room = roomsRepository.findById(request.getRoomId()).orElse(null);
        if(room != null) {
            room.setStatus(RoomStatus.USED.getStatus());
        }
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
            Rooms room = roomsRepository.findById(request.getRoomId()).orElse(null);
            contract.setRoom(room);
        }

        //set contract to tenant_contract
        contract.getTenantContracts().forEach(tenantContract -> tenantContract.setContract(contract));

        return contractsMapper.toContractsResponse(contractsRepository.save(contract));
    }

    @Override
    public void deleteContractById(String contractId) {
        Contracts contract = contractsRepository.findById(contractId).orElseThrow(null);
        contract.setStatus("disabled");
        // add room to contract
        Rooms room = roomsRepository.findById(contract.getRoom().getId()).orElse(null);
        if(room != null) {
            room.setStatus(RoomStatus.EMPTY.getStatus());
        }
        contract.setRoom(room);

        contractsMapper.toContractsResponse(contractsRepository.save(contract));
    }
}
