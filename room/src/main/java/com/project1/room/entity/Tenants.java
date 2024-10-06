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
public class Tenants {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String hometown;

    @Column
    private String identityCard;

    @Column
    private String vehiclePlate;

    @OneToMany(mappedBy = "tenant", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonIgnore
    private Set<TenantContract> tenantContracts;
}
