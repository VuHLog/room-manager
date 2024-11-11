package com.project1.room.service.serviceImpl;

import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.ServiceRoomsRepository;
import com.project1.room.dao.ServicesRepository;
import com.project1.room.dao.UsersRepository;
import com.project1.room.dto.request.ServiceRoomsRequest;
import com.project1.room.dto.response.ServiceRoomsResponse;
import com.project1.room.entity.*;
import com.project1.room.mapper.ServiceRoomsMapper;
import com.project1.room.service.ServiceRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ServiceRoomsServiceImpl implements ServiceRoomsService {
    @Autowired
    private ServiceRoomsRepository serviceRoomsRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private ServiceRoomsMapper serviceRoomsMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Page<ServiceRoomsResponse> getServiceRooms(Pageable pageable) {
        return serviceRoomsRepository.findByOrderByYearDescMonthDesc(pageable).map(serviceRoomsMapper::toServiceRoomsResponse);
    }

    @Override
    public Page<ServiceRoomsResponse> getServiceRoomsContain(String text, Pageable pageable) {
        return serviceRoomsRepository.findByService_NameContainsIgnoreCaseOrderByYearDescMonthDesc(text, pageable).map(serviceRoomsMapper::toServiceRoomsResponse);
    }

    @Override
    public ServiceRoomsResponse getById(String id) {
        ServiceRooms serviceRoom = serviceRoomsRepository.findById(id).orElse(null);
        return serviceRoomsMapper.toServiceRoomsResponse(serviceRoom);
    }

    @Override
    public ServiceRoomsResponse addServiceRoom(ServiceRoomsRequest request) {
        ServiceRooms serviceRoom = serviceRoomsMapper.toServiceRoom(request);

        // add Room
        Rooms room = roomsRepository.findById(request.getRoomId()).orElse(null);
        serviceRoom.setRoom(room);

        // add Service
        Services service = servicesRepository.findById(request.getServiceId()).orElse(null);
        serviceRoom.setService(service);

        return serviceRoomsMapper.toServiceRoomsResponse(serviceRoomsRepository.save(serviceRoom));
    }

    @Override
    public ServiceRoomsResponse updateServiceRoom(String serviceRoomId, ServiceRoomsRequest request) {
        ServiceRooms serviceRoom = serviceRoomsRepository.findById(serviceRoomId).orElseThrow(null);
        serviceRoomsMapper.updateServiceRoom(serviceRoom, request);

        if(!serviceRoom.getService().getId().equals(request.getServiceId())) {
            Services service = servicesRepository.findById(request.getServiceId()).orElse(null);
            serviceRoom.setService(service);
        }

        if(!serviceRoom.getRoom().getId().equals(request.getRoomId())) {
            Rooms room = roomsRepository.findById(request.getRoomId()).orElse(null);
            serviceRoom.setRoom(room);
        }

        return serviceRoomsMapper.toServiceRoomsResponse(serviceRoomsRepository.save(serviceRoom));
    }

    @Override
    public void deleteServiceRoomsById(String serviceRoomId) {
        serviceRoomsRepository.deleteById(serviceRoomId);
    }

    public boolean hasManager(String serviceRoomId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get store
        ServiceRooms serviceRooms = serviceRoomsRepository.findById(serviceRoomId).orElse(null);
        return user != null && serviceRooms != null && user.getId().equals(serviceRooms.getRoom().getBranch().getManager().getId());
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
