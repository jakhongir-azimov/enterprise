package com.company.service.impl;

import com.company.mapper.EmployeeMapper;
import com.company.model.dto.EmployeeDto;
import com.company.model.dto.ResponseDto;
import com.company.model.entity.DepartmentEntity;
import com.company.model.entity.EmployeeEntity;
import com.company.model.form.EmployeeForm;
import com.company.repository.DepartmentRepository;
import com.company.repository.EmployeeRepository;
import com.company.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final BaseService baseService;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<?> addEmployee(EmployeeForm employeeForm) {
        ResponseDto<?> response;
        EmployeeEntity employee;

        try {

            employee = employeeMapper.EMPLOYEE_ENTITY(employeeForm);

            DepartmentEntity department = departmentRepository.findDepartment(employeeForm.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));

            employee.setDepartment(department);
            employee = employeeRepository.save(employee);
            log.info("Employee created successfully");

            EmployeeDto dto = employeeMapper.EMPLOYEE_DTO(employee);
            response = baseService.convertResponseDto(dto, "Employee created successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Employee creation failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getEmployee(Integer id) {
        ResponseDto<?> response;

        response = baseService.convertResponseDto(employeeMapper.EMPLOYEE_DTO(
                employeeRepository.findEmployeeById(id)), "success", true, 200);

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<?> listEmployee(Integer page, Integer size) {
        ResponseDto<?> response;
        Page<EmployeeEntity> page1;

        try {
            PageRequest pageable = PageRequest.of(page, size);
            page1 = employeeRepository.findAllEmployeeWithStateTrue(pageable);

            List<EmployeeDto> empDtos = page1.getContent().stream()
                    .map(employeeMapper::EMPLOYEE_DTO)
                    .collect(Collectors.toList());

            log.info("Retrieved page {} of size {} from all employee.", page, size);
            response = baseService.convertResponseDto(empDtos, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Error retrieving all employees", e);
            response = baseService.convertResponseDto(null, "Interval Server Error", false, 500);
            return ResponseEntity.status(500).body(response);

        }
    }

    @Override
    public ResponseEntity<?> updateEmployee(Integer id, EmployeeForm empForm) {
        ResponseDto<?> response;

        try {
            DepartmentEntity department = departmentRepository.findDepartment(empForm.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));

            employeeRepository.updateEmployee(empForm.getFirstName(), empForm.getLastName(), empForm.getPatronymic(),
                    empForm.getAge(), empForm.getPassportSeries(), empForm.getPassportNumber(), empForm.getJshshir(),
                    empForm.getNationality(), empForm.getSalary(), empForm.getAddress(), department, id);
            log.info("Employee updated successfully");
            response = baseService.convertResponseDto(null, "Employee updated successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Employee update failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteEmployee(Integer id) {
        ResponseDto<?> response;

        try {
            String newPassportNumber = baseService.updateState(employeeRepository.findEmployeeById(id).getPassportNumber());

            employeeRepository.deleteEmployeeById(newPassportNumber, id);
            response = baseService.convertResponseDto(null, "Employee deleted successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Something went wrong while deleting employee {}", id);
            response = baseService.convertResponseDto(null, "Something went wrong while deleting employee", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }
}
