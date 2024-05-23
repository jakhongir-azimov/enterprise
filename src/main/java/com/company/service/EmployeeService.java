package com.company.service;

import com.company.model.form.EmployeeForm;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    ResponseEntity<?> addEmployee(EmployeeForm employeeForm);

    ResponseEntity<?> getEmployee(Integer id);

    ResponseEntity<?> listEmployee(Integer page, Integer size);

    ResponseEntity<?> updateEmployee(Integer id, EmployeeForm employeeForm);

    ResponseEntity<?> deleteEmployee(Integer id);
}
