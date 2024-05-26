package com.company.repository;

import com.company.model.entity.DepartmentEntity;
import com.company.model.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT e FROM EmployeeEntity e WHERE e.id = ?1 AND e.state = true ")
    Optional<EmployeeEntity> findEmployee(Integer employeeId);

    @Transactional(readOnly = true)
    @Query("SELECT e FROM EmployeeEntity e WHERE e.id = ?1 AND e.state = true ")
    EmployeeEntity findEmployeeById(Integer id);

    @Transactional
    @Query("SELECT e FROM EmployeeEntity e WHERE e.state = true ")
    Page<EmployeeEntity> findAllEmployeeWithStateTrue(PageRequest pageable);

    @Transactional
    @Modifying
    @Query("UPDATE EmployeeEntity SET firstName = ?1, lastName = ?2, patronymic = ?3, age = ?4, passportSeries = ?5," +
            " passportNumber = ?6, jshshir = ?7, nationality = ?8, salary = ?9, address = ?10, department = ?11 WHERE id = ?12 AND state = true ")
    void updateEmployee(String firstName, String lastName, String patronymic, int age, String passportSeries,
                        String passportNumber, String jshshir, String nationality, double salary, String address, DepartmentEntity department, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE EmployeeEntity e SET e.state = false, e.passportNumber = ?1 WHERE e.id = ?2")
    void deleteEmployeeById(String newPassportNumber, Integer id);
}
