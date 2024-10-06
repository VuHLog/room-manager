package com.project1.room.dao;

import com.project1.room.entity.Branches;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchesRepository extends JpaRepository<Branches, String> {
    Page<Branches> findByNameContainsIgnoreCase(String text, Pageable pageable);
}