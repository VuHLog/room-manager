package com.project1.room.dao.specifications;

import com.project1.room.entity.Invoices;
import com.project1.room.entity.Rooms;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.apache.catalina.Manager;
import org.springframework.data.jpa.domain.Specification;

public class InvoicesSpecification {
    public static Specification<Invoices> equalManagement(String managerId){
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> roomJoin = root.join("room", JoinType.INNER);
            Join<Object, Object> branchJoin = roomJoin.join("branch", JoinType.INNER);
            return criteriaBuilder.equal(branchJoin.get("manager").get("id"), managerId);
        };
    }

    public static Specification<Invoices> equalBranchId(String branchId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> roomJoin = root.join("room", JoinType.INNER);
            return criteriaBuilder.like(roomJoin.get("branch").get("id"), branchId);
        };
    }

    public static Specification<Invoices> equalRoomId(String roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("room").get("id"), roomId);
    }

    public static Specification<Invoices> equalStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paymentStatus"), status);
    }
}
