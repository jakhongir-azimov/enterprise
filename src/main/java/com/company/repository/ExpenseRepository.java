package com.company.repository;

import com.company.model.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT ee FROM ExpenseEntity ee WHERE ee.id = ?1 AND ee.state = true ")
    ExpenseEntity findExpenseById(Integer id);
}
