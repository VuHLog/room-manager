package com.project1.room.dao;

import com.project1.room.entity.Tenants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantsRepository extends JpaRepository<Tenants, String> {
    Page<Tenants> findByNameContainsIgnoreCase(String text, Pageable pageable);
}