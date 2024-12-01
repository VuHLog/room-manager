package com.project1.room.service.serviceImpl;

import com.project1.room.dao.EquipmentsRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.RoomEquipmentRepository;
import com.project1.room.dao.UsersRepository;
import com.project1.room.dao.specifications.RoomEquipmentSpecification;
import com.project1.room.dto.request.RoomEquipmentsRequest;
import com.project1.room.dto.response.RoomEquipmentsResponse;
import com.project1.room.entity.*;
import com.project1.room.mapper.RoomEquipmentsMapper;
import com.project1.room.service.RoomEquipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RoomEquipmentsServiceImpl implements RoomEquipmentsService {
    @Autowired
    private RoomEquipmentRepository roomEquipmentRepository;

    @Autowired
    private EquipmentsRepository equipmentsRepository;

    @Autowired
    private RoomsRepository roomsRepository;
    
    @Autowired
    private RoomEquipmentsMapper roomEquipmentsMapper;

    @Autowired
    private UsersRepository usersRepository;
    
    @Override
    public Page<RoomEquipmentsResponse> getRoomEquipments(String field, Integer pageNumber, Integer pageSize, String sort, String search, String roomId) {
        Specification<RoomEquipment> specs = Specification.where(null);

        if(!roomId.trim().isEmpty()){
            specs = specs.and(RoomEquipmentSpecification.equalRoomId(roomId));
        }

        if(!search.trim().isEmpty()){
            specs = specs.and(RoomEquipmentSpecification.likeEquipmentName(search));
        }

        Sort sortable = sort.equals("ASC")? Sort.by(field).ascending(): Sort.by(field).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortable);
        return roomEquipmentRepository.findAll(specs, pageable).map(roomEquipmentsMapper::toRoomEquipmentsResponse);
    }

    @Override
    public Page<RoomEquipmentsResponse> getRoomEquipmentsContain(String text, Pageable pageable) {
        return roomEquipmentRepository.findByEquipment_NameContainsIgnoreCase(text, pageable).map(roomEquipmentsMapper::toRoomEquipmentsResponse);
    }

    @Override
    public RoomEquipmentsResponse getById(String id) {
        RoomEquipment roomEquipment = roomEquipmentRepository.findById(id).orElse(null);
        return roomEquipmentsMapper.toRoomEquipmentsResponse(roomEquipment);
    }

    @Override
    public RoomEquipmentsResponse addRoomEquipment(RoomEquipmentsRequest request) {
        RoomEquipment roomEquipment = roomEquipmentsMapper.toRoomEquipment(request);
        
        // add Room
        Rooms room = roomsRepository.findById(request.getRoomId()).orElse(null);
        roomEquipment.setRoom(room);

        // add Service
        Equipments equipment = equipmentsRepository.findById(request.getEquipmentId()).orElse(null);
        roomEquipment.setEquipment(equipment);
        
        return roomEquipmentsMapper.toRoomEquipmentsResponse(roomEquipmentRepository.save(roomEquipment));
    }

    @Override
    public RoomEquipmentsResponse updateRoomEquipment(String serviceRoomId, RoomEquipmentsRequest request) {
        RoomEquipment roomEquipment = roomEquipmentRepository.findById(serviceRoomId).orElseThrow(null);
        roomEquipmentsMapper.updateRoomEquipment(roomEquipment, request);

        if(!roomEquipment.getEquipment().getId().equals(request.getEquipmentId())) {
            Equipments equipment = equipmentsRepository.findById(request.getEquipmentId()).orElse(null);
            roomEquipment.setEquipment(equipment);
        }

        if(!roomEquipment.getRoom().getId().equals(request.getRoomId())) {
            Rooms room = roomsRepository.findById(request.getRoomId()).orElse(null);
            roomEquipment.setRoom(room);
        }
        
        return roomEquipmentsMapper.toRoomEquipmentsResponse(roomEquipmentRepository.save(roomEquipment));
    }

    @Override
    public void deleteRoomEquipmentById(String serviceRoomId) {
        roomEquipmentRepository.deleteById(serviceRoomId);
    }

    public boolean hasManager(String roomEquipmentId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get roomEquipment
        RoomEquipment roomEquipment = roomEquipmentRepository.findById(roomEquipmentId).orElse(null);
        return user != null && roomEquipment != null && user.getId().equals(roomEquipment.getRoom().getBranch().getManager().getId());
    }

    public boolean isCreateForManager(String roomId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get store
        Rooms room = roomsRepository.findById(roomId).orElse(null);

        return user != null && room != null && user.getId().equals(room.getBranch().getManager().getId());
    }
}
