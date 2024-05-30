package com.company.service.impl;

import com.company.mapper.ExpenseMapper;
import com.company.model.dto.ExpenseDto;
import com.company.model.dto.ResponseDto;
import com.company.model.entity.EmployeeEntity;
import com.company.model.entity.ExpenseEntity;
import com.company.model.form.ExpenseForm;
import com.company.repository.EmployeeRepository;
import com.company.repository.ExpenseRepository;
import com.company.service.ExpenseService;
import com.company.service.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final BaseService baseService;
    private final EmployeeRepository employeeRepository;


    @Override
    public ResponseEntity<?> addExpense(ExpenseForm expenseForm) {

        ResponseDto<?> response;
        ExpenseEntity expense;

        try {
            expense = expenseMapper.EXPENSE_ENTITY(expenseForm);

            EmployeeEntity employee = employeeRepository.findEmployee(expenseForm.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

            expense.setEmployee(employee);
            expense = expenseRepository.save(expense);
            log.info("Expense created successfully");

            ExpenseDto dto = expenseMapper.EXPENSE_DTO(expense);
            response = baseService.convertResponseDto(dto, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            log.warn("Expense creation failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getExpense(Integer id) {
        ResponseDto<?> response;

        response = baseService.convertResponseDto(expenseMapper.EXPENSE_DTO(
                expenseRepository.findExpenseById(id)), "success", true, 200);

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<?> listExpense(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateExpense(Integer id, ExpenseForm expenseForm) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteExpense(Integer id) {
        return null;
    }
}
