package com.project1.room.dao.specifications;

import com.project1.room.entity.Contracts;
import com.project1.room.entity.Invoices;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;


public class ContractsSpecification {

    public static Specification<Contracts> equalStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Contracts> equalRoomId(String roomId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> roomJoin = root.join("room", JoinType.INNER);
            return criteriaBuilder.equal(roomJoin.get("id"), roomId);
        };
    }

    public static Specification<Contracts> equalManagement(String managerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> roomJoin = root.join("room", JoinType.INNER);
            Join<Object, Object> branchJoin = roomJoin.join("branch", JoinType.INNER);
            return criteriaBuilder.equal(branchJoin.get("manager").get("id"), managerId);
        };
    }

    public static Specification<Contracts> equalBranchId(String branchId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> roomJoin = root.join("room", JoinType.INNER);
            return criteriaBuilder.like(roomJoin.get("branch").get("id"), branchId);
        };
    }
}
