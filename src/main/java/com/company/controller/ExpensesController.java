package com.company.controller;

import com.company.model.form.ExpenseForm;
import com.company.service.ExpensesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense")
@Slf4j
@Tag(name = "Expenses")
@AllArgsConstructor
public class ExpensesController {

    private final ExpensesService expenseService;

    @Operation(
            description = "Add expense",
            summary = "add endpoint of expense"
    )
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/add")
    public ResponseEntity<?> createExpense(
            @RequestBody ExpenseForm expenseForm
    ) {
        log.info("Received create endpoint of expense request");
        return expenseService.addExpense(expenseForm);
    }

    @Operation(
            description = "Get expense",
            summary = "get endpoint of expense"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getExpense(
            @PathVariable("id") Integer id
    ) {
        log.info("Received get endpoint of expense request");
        return expenseService.getExpense(id);
    }

    @Operation(
            description = "Get list expense",
            summary = "get list endpoint of expense"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/list")
    public ResponseEntity<?> listExpense(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received get list endpoint of expense request");
        return expenseService.listExpense(page, size);
    }

    @Operation(
            description = "Update expense",
            summary = "update endpoint of expense"
    )
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExpense(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ExpenseForm expenseForm
    ) {
        log.info("Received update endpoint of expense request");
        return expenseService.updateExpense(id, expenseForm);
    }

    @Operation(
            description = "Delete expense",
            summary = "delete endpoint of expense"
    )
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable("id") Integer id
    ) {
        log.info("Received delete endpoint of expense request");
        return expenseService.deleteExpense(id);
    }

}
