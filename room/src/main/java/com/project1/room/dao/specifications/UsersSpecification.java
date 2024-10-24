package com.project1.room.dao.specifications;

import com.project1.room.entity.Users;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class UsersSpecification {

    public static Specification<Users> getUsersSpecificationContainsIgnoreCase(String text) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + text + "%");
            Predicate usernamePredicate = criteriaBuilder.like(root.get("username"), "%" + text + "%");
            Predicate phoneNumberPredicate = criteriaBuilder.like(root.get("phoneNumber"), "%" + text + "%");
            return criteriaBuilder.or(namePredicate, usernamePredicate, phoneNumberPredicate);
        };
    }
}
