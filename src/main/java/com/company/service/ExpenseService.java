package com.company.service;

import com.company.model.form.ExpenseForm;
import org.springframework.http.ResponseEntity;

public interface ExpenseService {
    ResponseEntity<?> addExpense(ExpenseForm expenseForm);

    ResponseEntity<?> getExpense(Integer id);

    ResponseEntity<?> listExpense(Integer page, Integer size);

    ResponseEntity<?> updateExpense(Integer id, ExpenseForm expenseForm);

    ResponseEntity<?> deleteExpense(Integer id);
}
