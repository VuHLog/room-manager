package com.project1.room.dao.specifications;

import com.project1.room.entity.Branches;
import com.project1.room.entity.Rooms;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class RoomsSpecification {

    public static Specification<Rooms> equalRoomNumber(String roomNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("roomNumber"), "%" + roomNumber + "%");
    }

    public static Specification<Rooms> equalManagerId(String managerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> branchJoin = root.join("branch", JoinType.INNER);
            return criteriaBuilder.equal(branchJoin.get("manager").get("id"), managerId);
        };
    }

    public static Specification<Rooms> equalBranchId(String branchId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("branch").get("id"), branchId);
    }

    public static Specification<Rooms> equalStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Rooms> equalCapacity(int capacity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("capacity"), capacity);
    }
}
