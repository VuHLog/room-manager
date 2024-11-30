package com.project1.room.dao.specifications;

import com.project1.room.entity.Invoices;
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
}
