package com.project1.room.dao;

import com.project1.room.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Services, String> {
    Page<Services> findByNameContainsIgnoreCase(String text, Pageable pageable);
}