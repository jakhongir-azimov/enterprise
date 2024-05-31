package com.company.service.impl;

import com.company.mapper.ExpenseMapper;
import com.company.model.dto.ExpenseDto;
import com.company.model.dto.ResponseDto;
import com.company.model.entity.EmployeeEntity;
import com.company.model.entity.ExpenseEntity;
import com.company.model.form.ExpenseForm;
import com.company.repository.EmployeeRepository;
import com.company.repository.ExpensesRepository;
import com.company.service.ExpensesService;
import com.company.service.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpensesRepository expenseRepository;
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
        ResponseDto<?> response;

        Page<ExpenseEntity> page1;

        try {
            PageRequest pageable = PageRequest.of(page, size);
            page1 = expenseRepository.findAllExpensesWithStateTrue(pageable);

            List<ExpenseDto> dtos = page1.getContent().stream()
                    .map(expenseMapper::EXPENSE_DTO)
                    .collect(Collectors.toList());

            log.info("Retrieved  page {} of size {} from all department.", page, size);
            response = baseService.convertResponseDto(dtos, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Error retrieving all expenses", e);
            response = baseService.convertResponseDto(null, "Interval Server Error", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> updateExpense(Integer id, ExpenseForm expenseForm) {

        ResponseDto<?> response;

        try {
            EmployeeEntity employee = employeeRepository.findEmployee(expenseForm.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

            expenseRepository.updateExpense(expenseForm.getAdsType(), expenseForm.getCost(),
                    expenseForm.getDuration(), employee, id);
            log.info("Expenses updated successfully");
            response = baseService.convertResponseDto(null, "Expenses updated successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Expense updated failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteExpense(Integer id) {

        ResponseDto<?> response;

        // TODO expense entity fix to unique == ...

        try {
            String delete = baseService.updateState(expenseRepository.findExpenseById(id).getAdsType());

            expenseRepository.deleteExpenseById(delete, id);
            response = baseService.convertResponseDto(null, "Expenses deleted successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Something went wrong while deleting expenses {}", id);
            response = baseService.convertResponseDto(null, "Something went wrong while deleting expenses", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }
}
