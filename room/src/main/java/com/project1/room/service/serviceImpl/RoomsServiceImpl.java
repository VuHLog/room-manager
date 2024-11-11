package com.project1.room.service.serviceImpl;

import com.project1.room.constants.RoomStatus;
import com.project1.room.dao.*;
import com.project1.room.dao.specifications.RoomsSpecification;
import com.project1.room.dto.request.RoomsRequest;
import com.project1.room.dto.response.RoomsResponse;
import com.project1.room.entity.*;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.RoomsMapper;
import com.project1.room.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsServiceImpl implements RoomsService {
    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private RoomsMapper roomsMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Page<RoomsResponse> getRooms(String field, Integer pageNumber, Integer pageSize, String sort, String searchText, String branchId, String managerId, String status, Integer capacity) {
        Specification<Rooms> specs = Specification.where(null);
        if (!searchText.trim().isEmpty()) {
            specs = specs.and(RoomsSpecification.equalRoomNumber(searchText));
        }

        if (!branchId.trim().isEmpty()) {
            specs = specs.and(RoomsSpecification.equalBranchId(branchId));
        }

        if (!managerId.trim().isEmpty()) {
            specs = specs.and(RoomsSpecification.equalManagerId(managerId));
        }

        if (!status.trim().isEmpty()) {
            specs = specs.and(RoomsSpecification.equalStatus(status));
        }
        if (capacity != null) {
            specs = specs.and(RoomsSpecification.equalCapacity(capacity));
        }


        Sort sortable = sort.equalsIgnoreCase("ASC") ? Sort.by(field).ascending() : Sort.by(field).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortable);

        return roomsRepository.findAll(specs, pageable).map(roomsMapper::toRoomsResponse);
    }

    @Override
    public Page<RoomsResponse> getRoomsByBranchId(String branchId, Pageable pageable) {
        return roomsRepository.findByBranch_Id(branchId, pageable).map(roomsMapper::toRoomsResponse);
    }

    @Override
    public RoomsResponse getById(String roomId) {
        Rooms room = roomsRepository.findById(roomId).orElse(null);
        return roomsMapper.toRoomsResponse(room);
    }

    @Override
    public RoomsResponse addRoom(RoomsRequest request) {
        // check branchId and room number existed
        if(roomsRepository.existsByBranch_IdAndRoomNumber(request.getBranchId(), request.getRoomNumber()))
            throw new AppException(ErrorCode.BRANCH_ROOM_NUMBER_EXISTED);

        Rooms room = roomsMapper.toRooms(request);
        room.setStatus(RoomStatus.EMPTY.getStatus());

        // add branch to room
        Branches branch = branchesRepository.findById(request.getBranchId()).orElseThrow(null);
        room.setBranch(branch);

        //set room to service room
        room.getServiceRooms().forEach(serviceRoom -> serviceRoom.setRoom(room));

        //set room to equipment room
        room.getRoomEquipments().forEach(roomEquipment -> roomEquipment.setRoom(room));

        return roomsMapper.toRoomsResponse(roomsRepository.save(room));
    }

    @Override
    public RoomsResponse updateRoom(String roomId, RoomsRequest request) {
        Rooms room = roomsRepository.findById(roomId).orElseThrow(null);

        if((!room.getRoomNumber().equals(request.getRoomNumber()) || !room.getBranch().getId().equals(request.getBranchId()))
                && roomsRepository.existsByBranch_IdAndRoomNumber(request.getBranchId(), request.getRoomNumber())
        ){
                throw new AppException(ErrorCode.BRANCH_ROOM_NUMBER_EXISTED);
        }

        roomsMapper.updateRoom(room, request);

        if(!room.getBranch().getId().equals(request.getBranchId())) {
            Branches branch = branchesRepository.findById(request.getBranchId()).orElse(null);
            room.setBranch(branch);
        }

        //set room to service room
        room.getServiceRooms().forEach(serviceRoom -> serviceRoom.setRoom(room));

        //set room to equipment room
        room.getRoomEquipments().forEach(roomEquipment -> roomEquipment.setRoom(room));

        return roomsMapper.toRoomsResponse(roomsRepository.save(room));
    }

    public void updateRoomStatus(String roomId, String status) {
        Rooms room = roomsRepository.findById(roomId).orElseThrow(null);
        room.setStatus(status);
        roomsMapper.toRoomsResponse(roomsRepository.save(room));
    }

    @Override
    public void deleteRoomById(String roomId) {
        updateRoomStatus(roomId, RoomStatus.DISABLED.getStatus());
    }

    public boolean hasManager(String roomId) {
        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get branch
        Rooms room = roomsRepository.findById(roomId).orElse(null);
        return user != null && room != null && user.getId().equals(room.getBranch().getManager().getId());
    }
}
