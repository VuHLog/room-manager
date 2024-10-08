package com.project1.room.service.serviceImpl;

import com.project1.room.dao.ServicesRepository;
import com.project1.room.dto.request.ServicesRequest;
import com.project1.room.dto.response.ServicesResponse;
import com.project1.room.entity.Services;
import com.project1.room.mapper.ServicesMapper;
import com.project1.room.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesMapper servicesMapper;

    @Override
    public Page<ServicesResponse> getServices(Pageable pageable) {
        return servicesRepository.findAll(pageable).map(servicesMapper::toServicesResponse);
    }

    @Override
    public Page<ServicesResponse> getServicesContain(String text, Pageable pageable) {
        return servicesRepository.findByNameContainsIgnoreCase(text, pageable).map(servicesMapper::toServicesResponse);
    }

    @Override
    public Page<ServicesResponse> getServicesByRoomId(String roomId, Pageable pageable) {
        return servicesRepository.findByServiceRooms_Room_IdOrderByServiceRooms_YearDescServiceRooms_MonthDesc(roomId, pageable).map(servicesMapper::toServicesResponse);
    }

    @Override
    public ServicesResponse getById(String ServiceId) {
        Services Service = servicesRepository.findById(ServiceId).orElseThrow(null);
        return servicesMapper.toServicesResponse(Service);
    }

    @Override
    public ServicesResponse addService(ServicesRequest request) {
        Services Service = servicesMapper.toService(request);
        return servicesMapper.toServicesResponse(servicesRepository.save(Service));
    }

    @Override
    public ServicesResponse updateService(String ServiceId, ServicesRequest request) {
        Services Service = servicesRepository.findById(ServiceId).orElseThrow(null);
        servicesMapper.updateService(Service, request);
        return servicesMapper.toServicesResponse(servicesRepository.save(Service));
    }

    @Override
    public void deleteServiceById(String ServiceId) {
        servicesRepository.deleteById(ServiceId);
    }
}
