package com.company.mapper;

import com.company.model.dto.CustomerDto;
import com.company.model.entity.CustomerEntity;
import com.company.model.form.CustomerForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings(
            @Mapping(source = "employee.id", target = "employeeId")
    )
    CustomerDto CUSTOMER_DTO(CustomerEntity customer);

    CustomerEntity CUSTOMER_ENTITY(CustomerForm customerForm);

}
