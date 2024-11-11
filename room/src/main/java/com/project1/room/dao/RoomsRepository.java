package com.project1.room.dao;

import com.project1.room.entity.Branches;
import com.project1.room.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, String>, JpaSpecificationExecutor<Rooms> {
    Page<Rooms> findByBranch_NameContainsIgnoreCase(String text, Pageable pageable);

    Page<Rooms> findByBranch_Id(String branchId, Pageable pageable);

    boolean existsByBranch_IdAndRoomNumber(String branchId, String roomNumber);

    Long countByStatus(String status);
}