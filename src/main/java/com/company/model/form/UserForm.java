package com.company.model.form;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

    private String firstName;

    private String lastName;

    @NotEmpty(message = "Email must not be empty and NULL")
    private String email;

    @NotEmpty(message = "Password must not be empty and NULL")
    private String password;

    @NotEmpty(message = "Role must not be empty and NULL")
    private String role;

}
