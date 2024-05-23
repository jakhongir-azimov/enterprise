package com.company.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private int age;

    private String passportSeries;

    private String passportNumber;

    private String jshshir;

    private String nationality;

    private double salary;

    private String address;

    private Integer departmentId;

}
