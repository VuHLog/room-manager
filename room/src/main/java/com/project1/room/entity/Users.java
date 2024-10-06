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
@Builder
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private String id;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String username;

    @Column
    private String password;

    private String avatarUrl;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private Set<UserRole> user_roles;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private Set<Branches> branches;
}