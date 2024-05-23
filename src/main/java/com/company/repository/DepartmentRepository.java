package com.company.repository;

import com.company.model.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT d FROM DepartmentEntity d WHERE d.id = ?1 AND d.state = true ")
    Optional<DepartmentEntity> findDepartment(Integer departmentId);

    @Transactional(readOnly = true)
    @Query("SELECT d FROM DepartmentEntity d WHERE d.id = ?1 AND d.state = true ")
    DepartmentEntity findDepartmentById(Integer id);

    @Transactional
    @Query("SELECT d FROM DepartmentEntity d WHERE d.state = true ")
    Page<DepartmentEntity> findAllUsersWithStateTrue(PageRequest pageable);

    @Transactional
    @Modifying
    @Query("UPDATE DepartmentEntity SET name = ?1 WHERE id = ?2 AND state = true ")
    void updateDepartment(String name, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE DepartmentEntity d SET d.state = false , d.name = ?1 WHERE d.id = ?2 ")
    void deleteDepartmentById(String newName, Integer id);
}
