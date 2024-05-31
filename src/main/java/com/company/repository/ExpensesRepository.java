package com.company.repository;

import com.company.model.entity.EmployeeEntity;
import com.company.model.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpenseEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT ee FROM ExpenseEntity ee WHERE ee.id = ?1 AND ee.state = true ")
    ExpenseEntity findExpenseById(Integer id);

    @Transactional
    @Query("SELECT e FROM ExpenseEntity e WHERE e.state = true ")
    Page<ExpenseEntity> findAllExpensesWithStateTrue(PageRequest pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ExpenseEntity SET adsType = ?1, cost = ?2, duration = ?3, employee = ?4 WHERE id = ?5 AND state = true ")
    void updateExpense(String adsType, Double cost, Integer duration, EmployeeEntity employee, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE ExpenseEntity e SET e.state = false, e.adsType = ?1 WHERE e.id = ?2 ")
    void deleteExpenseById(String delete, Integer id);

}
