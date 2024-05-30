package com.company.mapper;

import com.company.model.dto.ExpenseDto;
import com.company.model.entity.ExpenseEntity;
import com.company.model.form.ExpenseForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mappings({
            @Mapping(source = "employee.id", target = "employeeId")
    })
    ExpenseDto EXPENSE_DTO(ExpenseEntity expense);

    ExpenseEntity EXPENSE_ENTITY(ExpenseForm expenseForm);

}
