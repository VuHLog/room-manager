package com.project1.room.dao.specifications;

import com.project1.room.entity.Contracts;
import com.project1.room.entity.Invoices;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ReportSpecification {
    public static Specification<Invoices> hasMonth(int month) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("month"), month);
    }

    public static Specification<Invoices> hasYear(int year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("year"), year);
    }

    public static Specification<Invoices> hasBranch(String branchId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object,Object> roomJoin = root.join("room", JoinType.INNER);
            Join<Object,Object> branchJoin = roomJoin.join("branch", JoinType.INNER);
            return criteriaBuilder.equal(branchJoin.get("id"), branchId);
        };
    }

    public static Specification<Invoices> hasRoom(String roomId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object,Object> roomJoin = root.join("room", JoinType.INNER);
            return criteriaBuilder.equal(roomJoin.get("id"), roomId);
        };
    }

    public static Specification<Invoices> hasPaymentStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paymentStatus"), status);
    }
}
