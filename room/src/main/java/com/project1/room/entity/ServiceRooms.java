package com.project1.room.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "service_rooms")
public class ServiceRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private int usage_quantity;

    @Column
    private int price;

    @Column
    private int year;

    @Column
    private int month;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;

}
