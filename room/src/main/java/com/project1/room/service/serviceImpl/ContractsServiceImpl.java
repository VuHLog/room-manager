package com.project1.room.service.serviceImpl;

import com.project1.room.constants.ContractStatus;
import com.project1.room.constants.RoomStatus;
import com.project1.room.dao.ContractsRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.UsersRepository;
import com.project1.room.dao.specifications.ContractsSpecification;
import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.entity.Contracts;
import com.project1.room.entity.Rooms;
import com.project1.room.entity.ServiceRooms;
import com.project1.room.entity.Users;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.ContractsMapper;
import com.project1.room.service.ContractsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class ContractsServiceImpl implements ContractsService {
    private final ContractsRepository contractsRepository;

    private final RoomsRepository roomsRepository;

    private final ContractsMapper contractsMapper;

    private final UsersRepository usersRepository;


    public ContractsServiceImpl(ContractsRepository contractsRepository, RoomsRepository roomsRepository, ContractsMapper contractsMapper, UsersRepository usersRepository) {
        this.contractsRepository = contractsRepository;
        this.roomsRepository = roomsRepository;
        this.contractsMapper = contractsMapper;
        this.usersRepository = usersRepository;
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
        String roomId = request.getRoomId();

        // get room contract status enabled
        List<Contracts> roomContractsEnabled = getContractsByStatusAndRoomId(ContractStatus.ENABLED.getStatus(), roomId);
        if(!roomContractsEnabled.isEmpty()){
            throw new AppException(ErrorCode.ROOM_HAS_CONTRACT);
        }

        Contracts contract = contractsMapper.toContract(request);

        contract.setStatus(ContractStatus.ENABLED.getStatus());

        // add room to contract
        Rooms room = roomsRepository.findById(roomId).orElse(null);
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

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void autoUpdateContractStatus() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        List<Contracts> contracts = contractsRepository.findAll();
        contracts.forEach(contract -> {
            if(currentTime.after(contract.getEndDate())){
                contract.setStatus(ContractStatus.DISABLED.getStatus());

                //update room status
                roomsRepository.findById(contract.getRoom().getId()).ifPresent(room -> {
                    room.setStatus(RoomStatus.EMPTY.getStatus());
                    roomsRepository.save(room);
                });
            }
        });
        contractsRepository.saveAll(contracts);
    }

    @Override
    public List<Contracts> getContractsByStatusAndRoomId(String status, String roomId) {
        // check room contract status
        Specification<Contracts> specs = Specification.where(null);
        specs = specs.and(ContractsSpecification.equalRoomId(roomId)).and(ContractsSpecification.equalStatus(status));
        return contractsRepository.findAll(specs);
    }

    public boolean hasManager(String contractId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get store
        Contracts contract = contractsRepository.findById(contractId).orElse(null);
        return user != null && contract != null && user.getId().equals(contract.getRoom().getBranch().getManager().getId());
    }

    public boolean isCreateForManager(String roomId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get room
        Rooms room = roomsRepository.findById(roomId).orElse(null);

        return user != null && room != null && user.getId().equals(room.getBranch().getManager().getId());
    }
}
