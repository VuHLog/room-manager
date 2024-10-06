package com.project1.room.service;

import com.project1.room.dto.request.TenantsRequest;
import com.project1.room.dto.response.TenantsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantsService {
    Page<TenantsResponse> getTenants(Pageable pageable);

    Page<TenantsResponse> getTenantsContain(String text, Pageable pageable);

    TenantsResponse getById(String id);

    TenantsResponse addTenant(TenantsRequest request);

    TenantsResponse updateTenant(String tenantId, TenantsRequest request);

    void deleteTenantById(String tenantId);
}
