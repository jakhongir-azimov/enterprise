package com.company.model.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeForm {

    private String firstName;

    private String lastName;

    private String patronymic;

    private int age;

    private String passportSeries;

    @NotEmpty(message = "Must not be empty and NULL")
    @Pattern(regexp = "\\d{6,9}", message = "Passpot number must be between 6 and 9 digits")
    private String passportNumber;

    @NotEmpty(message = "Must not be empty and NULL")
    private String jshshir;

    private String nationality;

    private double salary;

    private String address;

    @NotNull(message = "Department ID must not be null")
    private Integer departmentId;

}
