package com.project1.room.dao;

import com.project1.room.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    Page<Store> findByBranch_NameContainsIgnoreCase(String text, Pageable pageable);
}