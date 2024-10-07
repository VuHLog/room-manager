package com.project1.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Equipments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @OneToMany(mappedBy = "equipment")
    @JsonIgnore
    private Set<RoomEquipment> roomEquipments;

    @OneToMany(mappedBy = "equipment")
    @JsonIgnore
    private Set<Store> stores;
}
