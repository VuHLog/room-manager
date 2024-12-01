package com.project1.room.dao.specifications;

import com.project1.room.entity.ServiceRooms;
import org.springframework.data.jpa.domain.Specification;

public class ServiceRoomSpecification {
    public static Specification<ServiceRooms> equalRoomId(String roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("room").get("id"), roomId);
    }

    public static Specification<ServiceRooms> likeServiceName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("service").get("name"), "%" + name + "%");
    }
}
