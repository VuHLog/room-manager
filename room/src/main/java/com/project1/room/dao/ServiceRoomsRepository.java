package com.project1.room.dao;

import com.project1.room.entity.ServiceRooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRoomsRepository extends JpaRepository<ServiceRooms, String> {
    Page<ServiceRooms> findByService_NameContainsIgnoreCaseOrderByYearDescMonthDesc(String name, Pageable pageable);

    Page<ServiceRooms> findByOrderByYearDescMonthDesc(Pageable pageable);
}