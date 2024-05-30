package com.company.controller;

import com.company.model.form.EmployeeForm;
import com.company.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
@Tag(name = "Employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            description = "Add employee",
            summary = "add endpoint of employee"
    )
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(
            @Valid @RequestBody EmployeeForm employeeForm
    ) {
        log.info("Received create endpoint of employee request");
        return employeeService.addEmployee(employeeForm);
    }

    @Operation(
            description = "Get employee",
            summary = "get endpoint of employee"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEmployee(
            @PathVariable("id") Integer id
    ) {
        log.info("Received get endpoint of employee request");
        return employeeService.getEmployee(id);
    }

    @Operation(
            description = "Get list employee",
            summary = "get list endpoint of employee"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/list")
    public ResponseEntity<?> listEmployee(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received get list endpoint of employee request");
        return employeeService.listEmployee(page, size);
    }

    @Operation(
            description = "Update employee",
            summary = "update endpoint of employee"
    )
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("id") Integer id,
            @Valid @RequestBody EmployeeForm employeeForm
    ) {
        log.info("Received update endpoint of employee");
        return employeeService.updateEmployee(id, employeeForm);
    }

    @Operation(
            description = "Delete employee",
            summary = "delete endpoint of employee"
    )
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable("id") Integer id
    ) {
        log.info("Received delete endpoint of employee");
        return employeeService.deleteEmployee(id);
    }


}
