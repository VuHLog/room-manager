package com.project1.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Contracts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private Timestamp startDate;

    @Column
    private Timestamp endDate;

    @Column
    private int price;

    @Column
    private int depositAmount;

    @Column
    private String description;

    @Column
    private String status;

    @OneToMany(mappedBy = "contract", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonIgnore
    private Set<TenantContract> tenantContracts;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    @PrePersist
    protected void onCreate() {
        startDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
