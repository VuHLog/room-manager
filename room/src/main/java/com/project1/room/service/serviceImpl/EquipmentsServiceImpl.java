package com.project1.room.service.serviceImpl;

import com.project1.room.dao.EquipmentsRepository;
import com.project1.room.dao.UsersRepository;
import com.project1.room.dto.request.EquipmentsRequest;
import com.project1.room.dto.response.EquipmentsResponse;
import com.project1.room.entity.Equipments;
import com.project1.room.entity.Users;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.EquipmentsMapper;
import com.project1.room.service.EquipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EquipmentsServiceImpl implements EquipmentsService {
    @Autowired
    private EquipmentsRepository equipmentsRepository;

    @Autowired
    private EquipmentsMapper equipmentsMapper;

    @Override
    public Page<EquipmentsResponse> getEquipments(Pageable pageable) {
        return equipmentsRepository.findAll(pageable).map(equipmentsMapper::toEquipmentsResponse);
    }

    @Override
    public Page<EquipmentsResponse> getEquipmentsContain(String text, Pageable pageable) {
        return equipmentsRepository.findByNameContainsIgnoreCase(text, pageable).map(equipmentsMapper::toEquipmentsResponse);
    }

    @Override
    public EquipmentsResponse getById(String EquipmentId) {
        Equipments Equipment = equipmentsRepository.findById(EquipmentId).orElseThrow(null);
        return equipmentsMapper.toEquipmentsResponse(Equipment);
    }

    @Override
    public EquipmentsResponse addEquipment(EquipmentsRequest request) {
        Equipments Equipment = equipmentsMapper.toEquipment(request);
        return equipmentsMapper.toEquipmentsResponse(equipmentsRepository.save(Equipment));
    }

    @Override
    public EquipmentsResponse updateEquipment(String EquipmentId, EquipmentsRequest request) {
        Equipments Equipment = equipmentsRepository.findById(EquipmentId).orElseThrow(null);
        equipmentsMapper.updateEquipment(Equipment, request);
        return equipmentsMapper.toEquipmentsResponse(equipmentsRepository.save(Equipment));
    }

    @Override
    public void deleteEquipmentById(String EquipmentId) {
        equipmentsRepository.deleteById(EquipmentId);
    }
}
