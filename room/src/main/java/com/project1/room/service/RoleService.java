package com.project1.room.service;


import com.project1.room.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    public List<RoleResponse> getRoles();

    public RoleResponse getById(String id);
}
