package com.company.mapper;

import com.company.model.dto.EmployeeDto;
import com.company.model.entity.EmployeeEntity;
import com.company.model.form.EmployeeForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mappings({
            @Mapping(source = "department.id", target = "departmentId")
    })
    EmployeeDto EMPLOYEE_DTO(EmployeeEntity employee);

    EmployeeEntity EMPLOYEE_ENTITY(EmployeeForm employeeForm);

}
