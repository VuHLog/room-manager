package com.project1.room.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private int amount;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;
}
