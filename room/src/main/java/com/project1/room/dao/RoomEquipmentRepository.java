package com.project1.room.dao;

import com.project1.room.entity.RoomEquipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomEquipmentRepository extends JpaRepository<RoomEquipment, String>, JpaSpecificationExecutor<RoomEquipment> {
    Page<RoomEquipment> findByEquipment_NameContainsIgnoreCase(String name, Pageable pageable);
}