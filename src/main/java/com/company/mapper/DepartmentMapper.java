package com.company.mapper;

import com.company.model.dto.DepartmentDto;
import com.company.model.entity.DepartmentEntity;
import com.company.model.form.DepartmentForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto DEPARTMENT_DTO(DepartmentEntity department);

    DepartmentEntity DEPARTMENT_ENTITY(DepartmentForm departmentForm);

}
