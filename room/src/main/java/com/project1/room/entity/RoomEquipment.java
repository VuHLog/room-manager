package com.project1.room.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room_equipment")
public class RoomEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String status;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipments equipment;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
