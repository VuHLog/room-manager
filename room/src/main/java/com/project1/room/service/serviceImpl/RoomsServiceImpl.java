package com.project1.room.service.serviceImpl;

import com.project1.room.constants.RoomStatus;
import com.project1.room.dao.*;
import com.project1.room.dto.request.RoomsRequest;
import com.project1.room.dto.response.RoomsResponse;
import com.project1.room.entity.Branches;
import com.project1.room.entity.Equipments;
import com.project1.room.entity.Rooms;
import com.project1.room.entity.Services;
import com.project1.room.mapper.RoomsMapper;
import com.project1.room.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<RoomsResponse> getRooms(Pageable pageable) {
        return roomsRepository.findAll(pageable).map(roomsMapper::toRoomsResponse);
    }

    @Override
    public Page<RoomsResponse> getRoomsContain(String text, Pageable pageable) {
        return roomsRepository.findByBranch_NameContainsIgnoreCase(text, pageable).map(roomsMapper::toRoomsResponse);
    }

    @Override
    public RoomsResponse getById(String roomId) {
        Rooms room = roomsRepository.findById(roomId).orElse(null);
        return roomsMapper.toRoomsResponse(room);
    }

    @Override
    public RoomsResponse addRoom(RoomsRequest request) {
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
}
