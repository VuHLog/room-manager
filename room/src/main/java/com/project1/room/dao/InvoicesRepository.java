package com.project1.room.dao;

import com.project1.room.entity.Invoices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoices, String>, JpaSpecificationExecutor<Invoices> {
    Optional<List<Invoices>> findByRoom_IdAndYearAndMonth(String roomId, int year, int month);
}