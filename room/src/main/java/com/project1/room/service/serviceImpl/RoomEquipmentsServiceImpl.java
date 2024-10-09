package com.project1.room.service.serviceImpl;

import com.project1.room.dao.EquipmentsRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.RoomEquipmentRepository;
import com.project1.room.dto.request.RoomEquipmentsRequest;
import com.project1.room.dto.response.RoomEquipmentsResponse;
import com.project1.room.entity.Equipments;
import com.project1.room.entity.RoomEquipment;
import com.project1.room.entity.Rooms;
import com.project1.room.entity.Services;
import com.project1.room.mapper.RoomEquipmentsMapper;
import com.project1.room.service.RoomEquipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    @Override
    public Page<RoomEquipmentsResponse> getRoomEquipments(Pageable pageable) {
        return roomEquipmentRepository.findAll(pageable).map(roomEquipmentsMapper::toRoomEquipmentsResponse);
    }

    @Override
    public Page<RoomEquipmentsResponse> getRoomEquipmentsContain(String text, Pageable pageable) {
        return null;
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
}
