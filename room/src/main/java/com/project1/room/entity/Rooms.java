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
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String roomNumber;

    @Column
    private int capacity;

    @Column
    private String description;

    @Column
    private String status;

    @Column
    private int price;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branches branch;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH}, orphanRemoval = true)
    @JsonIgnore
    private Set<RoomEquipment> roomEquipments;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonIgnore
    private Set<ServiceRooms> serviceRooms;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Contracts> contracts;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Invoices> invoices;

}
