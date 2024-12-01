package com.project1.room.dao.specifications;

import com.project1.room.entity.RoomEquipment;
import com.project1.room.entity.ServiceRooms;
import org.springframework.data.jpa.domain.Specification;

public class RoomEquipmentSpecification {
    public static Specification<RoomEquipment> equalRoomId(String roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("room").get("id"), roomId);
    }

    public static Specification<RoomEquipment> likeEquipmentName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("equipment").get("name"), "%" + name + "%");
    }
}
