package com.project1.room.dao;

import com.project1.room.entity.Equipments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentsRepository extends JpaRepository<Equipments, String> {
    Page<Equipments> findByNameContainsIgnoreCase(String text, Pageable pageable);

    Page<Equipments> findByRoomEquipments_Room_Id(String roomId, Pageable pageable);
}