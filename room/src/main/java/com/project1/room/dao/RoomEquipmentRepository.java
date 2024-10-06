package com.project1.room.dao;

import com.project1.room.entity.RoomEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomEquipmentRepository extends JpaRepository<RoomEquipment, String> {
}