package com.project1.room.dao;

import com.project1.room.entity.UserRole;
import com.project1.room.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByUser_IdAndRole_Id(String id, String id1);

    @Modifying
    @Query("DELETE FROM UserRole ur WHERE ur.user = :user")
    void deleteByUser(Users user);

    @Modifying
    @Query(value = "DELETE ur from user_role as ur where ur.user_id = :userId",nativeQuery = true)
    void deleteByUserId(String userId);
}
