package com.project1.room.service;

import com.project1.room.dto.request.ServicesRequest;
import com.project1.room.dto.response.ServicesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicesService {
    Page<ServicesResponse> getServices(Pageable pageable);

    Page<ServicesResponse> getServicesContain(String text, Pageable pageable);

    ServicesResponse getById(String id);

    ServicesResponse addService(ServicesRequest request);

    ServicesResponse updateService(String serviceId, ServicesRequest request);

    void deleteServiceById(String serviceId);
}
