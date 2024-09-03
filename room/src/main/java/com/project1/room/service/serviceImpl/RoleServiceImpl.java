package com.project1.room.service.serviceImpl;

import com.project1.room.dao.RoleRepository;
import com.project1.room.dto.response.RoleResponse;
import com.project1.room.mapper.RoleMapper;
import com.project1.room.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleResponse getById(String id) {
        return roleMapper.toRoleResponse(roleRepository.findById(id).get());
    }

    @Override
    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

}
