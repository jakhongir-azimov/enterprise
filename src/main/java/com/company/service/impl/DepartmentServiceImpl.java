package com.company.service.impl;

import com.company.mapper.DepartmentMapper;
import com.company.mapper.EmployeeMapper;
import com.company.model.dto.DepartmentDto;
import com.company.model.dto.ResponseDto;
import com.company.model.entity.DepartmentEntity;
import com.company.model.entity.EmployeeEntity;
import com.company.model.form.DepartmentForm;
import com.company.repository.DepartmentRepository;
import com.company.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final BaseService baseService;


    @Override
    public ResponseEntity<?> addDepartment(DepartmentForm departmentForm) {

        ResponseDto<?> response;

        DepartmentEntity department;

        try {
            department = departmentRepository.save(departmentMapper.DEPARTMENT_ENTITY(departmentForm));
            log.info("Department created successfully");
            response = baseService.convertResponseDto(department, "success", true, 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Department creation failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getDepartment(Integer id) {

        ResponseDto<?> response;

        response = baseService.convertResponseDto(departmentMapper.DEPARTMENT_DTO(
                departmentRepository.findDepartmentById(id)), "success", true, 200);

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<?> listDepartment(Integer page, Integer size) {

        ResponseDto<?> response;

        Page<DepartmentEntity> page1;

        try {
            PageRequest pageable = PageRequest.of(page, size);
            page1 = departmentRepository.findAllUsersWithStateTrue(pageable);

            List<DepartmentDto> dtos = page1.getContent().stream()
                    .map(departmentMapper::DEPARTMENT_DTO)
                    .collect(Collectors.toList());

            log.info("Retrieved page {} of size {} from all department.", page, size);
            response = baseService.convertResponseDto(dtos, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Error retrieving all department", e);
            response = baseService.convertResponseDto(null, "Interval Server Error", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> updateDepartment(Integer id, DepartmentForm departmentForm) {

        ResponseDto<?> response;

        try {
            departmentRepository.updateDepartment(
                    departmentForm.getName(), id
            );
            log.info("Department updated successfully");
            response = baseService.convertResponseDto(null, "Department updated successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Department updated failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteDepartment(Integer id) {

        ResponseDto<?> response;

        try {
            String newName = baseService.updateState(departmentRepository.findDepartmentById(id).getName());

            departmentRepository.deleteDepartmentById(newName, id);
            response = baseService.convertResponseDto(null, "Department deleted successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Something went wrong while deleting department {}", id);
            response = baseService.convertResponseDto(null, "Something went wrong while deleting department", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }
}
