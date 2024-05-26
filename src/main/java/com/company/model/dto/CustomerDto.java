package com.company.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String passportSeries;

    private String passportNumber;

    private String jshshir;

    private String address;

    private Integer employeeId;

}
