package com.project1.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.Manager;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Branches {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Users manager;

    @OneToMany(mappedBy = "branch",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}
    )
    @JsonIgnore
    private Set<Rooms> rooms;

    @OneToMany(mappedBy = "branch",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}
    )
    @JsonIgnore
    private Set<Store> stores;
}
