package com.project1.room.dao;

import com.project1.room.entity.Invoices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoices, String> {
    Page<Invoices> findByOrderByYearDescMonthDesc(Pageable pageable);
}