package com.project1.room.dao.specifications;

import com.project1.room.entity.Branches;
import com.project1.room.entity.Users;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class BranchesSpecification {

    public static Specification<Branches> equalNameOrAddress(String text) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + text + "%");
            Predicate addressPredicate = criteriaBuilder.like(root.get("address"), "%" + text + "%");
            return criteriaBuilder.or(namePredicate, addressPredicate);
        };
    }

    public static Specification<Branches> equalManagerId(String managerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("manager").get("id"), "%" + managerId + "%" );
    }
}
