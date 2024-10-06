package com.project1.room.dao;

import com.project1.room.entity.TenantContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantContractRepository extends JpaRepository<TenantContract, String> {
}
