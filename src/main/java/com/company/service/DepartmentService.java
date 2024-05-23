package com.company.service;

import com.company.model.form.DepartmentForm;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    ResponseEntity<?> addDepartment(DepartmentForm departmentForm);

    ResponseEntity<?> getDepartment(Integer id);

    ResponseEntity<?> listDepartment(Integer page, Integer size);

    ResponseEntity<?> updateDepartment(Integer id, DepartmentForm departmentForm);

    ResponseEntity<?> deleteDepartment(Integer id);
}
