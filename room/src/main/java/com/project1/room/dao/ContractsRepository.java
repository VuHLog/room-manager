package com.project1.room.dao;

import com.project1.room.entity.Contracts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contracts, String> {
    Page<Contracts> findByDescriptionContainsIgnoreCase(String text, Pageable pageable);

    Contracts findByStatusAndRoom_Id(String status, String roomId);
}