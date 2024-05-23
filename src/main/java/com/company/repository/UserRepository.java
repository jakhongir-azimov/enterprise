package com.company.repository;

import com.company.enums.Role;
import com.company.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    @Transactional(readOnly = true)
    @Query("SELECT u FROM UserEntity u WHERE u.id = ?1 AND u.state = true ")
    UserEntity findUserById(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity SET firstName = ?1, lastName = ?2, email = ?3, password = ?4, role = ?5 WHERE id = ?6 AND state = true ")
    void updateUser(String firstName, String lastName, String email, String password, Role role, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.state = false, u.email = ?1 WHERE u.id = ?2")
    void deleteUserById(String userName, Integer id);

    @Transactional
    @Query("SELECT u FROM UserEntity u WHERE u.state = true ")
    Page<UserEntity> findAllUsersWithStateTrue(PageRequest pageable);
}
