package com.company.model.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseForm {

    private String adsType;

    private Double cost;

    private Integer duration;

    @NotNull(message = "Employee ID must not be NULL")
    private Integer employeeId;


}
