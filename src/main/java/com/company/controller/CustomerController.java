package com.company.controller;

import com.company.model.form.CustomerForm;
import com.company.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@Slf4j
@Tag(name = "Customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            description = "Add customer",
            summary = "add endpoint of customer"
    )
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/add")
    public ResponseEntity<?> createCustomer(
            @Valid @RequestBody CustomerForm customerForm
    ) {
        log.info("Received create endpoint of customer request");
        return customerService.addCustomer(customerForm);
    }

    @Operation(
            description = "Get customer",
            summary = "get endpoint of customer"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCustomer(
            @PathVariable("id") Integer id
    ) {
        log.info("Received get endpoint of customer request");
        return customerService.getCustomer(id);
    }

    @Operation(
            description = "Get list customers",
            summary = "get list endpoint of customers"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/list")
    public ResponseEntity<?> listCustomer(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received get list endpoint of customer request");
        return customerService.listCustomer(page, size);
    }

    @Operation(
            description = "Update customer",
            summary = "update endpoint of customer"
    )
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable("id") Integer id,
            @Valid @RequestBody CustomerForm customerForm
    ) {
        log.info("Received update endpoint of customer");
        return customerService.updateCustomer(id, customerForm);
    }

    @Operation(
            description = "Delete customer",
            summary = "delete endpoint of customer"
    )
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(
            @PathVariable("id") Integer id
    ) {
        log.info("Received delete endpoint of customer");
        return customerService.deleteCustomer(id);
    }


}
