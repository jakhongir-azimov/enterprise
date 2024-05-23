package com.company.controller;

import com.company.model.form.DepartmentForm;
import com.company.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/department")
@Slf4j
@Tag(name = "Department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(
            description = "Add department",
            summary = "add endpoint of department"
    )
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/add")
    public ResponseEntity<?> createDepartment(
            @RequestBody DepartmentForm departmentForm
    ) {
        log.info("Received create endpoint of department request");
        return departmentService.addDepartment(departmentForm);
    }

    @Operation(
            description = "Get department",
            summary = "get endpoint of department"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDepartment(
            @PathVariable("id") Integer id
    ) {
        log.info("Received get endpoint of department request");
        return departmentService.getDepartment(id);
    }

    @Operation(
            description = "Get list department",
            summary = "get list endpoint of department"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/list")
    public ResponseEntity<?> listDepartment(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received get list endpoint of department request");
        return departmentService.listDepartment(page, size);
    }

    @Operation(
            description = "Update department",
            summary = "update endpoint of department"
    )
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(
            @PathVariable("id") Integer id,
            @Valid @RequestBody DepartmentForm departmentForm
    ) {
        log.info("Received update endpoint of department request");
        return departmentService.updateDepartment(id, departmentForm);
    }

    @Operation(
            description = "Delete department",
            summary = "delete endpoint of department"
    )
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(
            @PathVariable("id") Integer id
    ) {
        log.info("Received delete endpoint of department request");
        return departmentService.deleteDepartment(id);
    }


}
