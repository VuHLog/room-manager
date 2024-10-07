package com.project1.room.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private int quantity;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branches branch;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipments equipment;
}
