package com.project1.room.service.serviceImpl;

import com.project1.room.dao.TenantsRepository;
import com.project1.room.dao.UsersRepository;
import com.project1.room.dto.request.TenantsRequest;
import com.project1.room.dto.response.TenantsResponse;
import com.project1.room.entity.Tenants;
import com.project1.room.entity.Users;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.TenantsMapper;
import com.project1.room.service.TenantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantsServiceImpl implements TenantsService {
    @Autowired
    private TenantsRepository tenantsRepository;

    @Autowired
    private TenantsMapper tenantsMapper;

    @Override
    public Page<TenantsResponse> getTenants(Pageable pageable) {
        return tenantsRepository.findAll(pageable).map(tenantsMapper::toTenantsResponse);
    }

    @Override
    public Page<TenantsResponse> getTenantsContain(String text, Pageable pageable) {
        return tenantsRepository.findByNameContainsIgnoreCase(text, pageable).map(tenantsMapper::toTenantsResponse);
    }

    @Override
    public TenantsResponse getById(String tenantId) {
        Tenants Tenant = tenantsRepository.findById(tenantId).orElseThrow(null);
        return tenantsMapper.toTenantsResponse(Tenant);
    }

    @Override
    public TenantsResponse addTenant(TenantsRequest request) {
        Tenants Tenant = tenantsMapper.toTenant(request);
        return tenantsMapper.toTenantsResponse(tenantsRepository.save(Tenant));
    }

    @Override
    public TenantsResponse updateTenant(String TenantId, TenantsRequest request) {
        Tenants Tenant = tenantsRepository.findById(TenantId).orElseThrow(null);
        tenantsMapper.updateTenant(Tenant, request);
        return tenantsMapper.toTenantsResponse(tenantsRepository.save(Tenant));
    }

    @Override
    public void deleteTenantById(String TenantId) {
        tenantsRepository.deleteById(TenantId);
    }
}
