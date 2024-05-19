package com.company.service;

import com.company.auth.AuthenticationRequest;
import com.company.auth.AuthenticationResponse;
import com.company.auth.RegisterRequest;
import com.company.model.form.BaseForm;
import com.company.model.form.UserForm;
import org.springframework.http.ResponseEntity;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<?> signUp(UserForm userForm);

    ResponseEntity<?> signIn(BaseForm baseForm);

    ResponseEntity<?> getUser(int id);

    ResponseEntity<?> listUsers(int page, int size);

    ResponseEntity<?> updateUser(int id, UserForm userForm);

    ResponseEntity<?> deleteUser(int id);
}
