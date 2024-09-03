package com.project1.room.dao;

import com.project1.room.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRoleName(String roleName);

}