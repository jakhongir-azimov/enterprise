package com.company.service;

import com.company.auth.AuthenticationRequest;
import com.company.auth.AuthenticationResponse;
import com.company.auth.RegisterRequest;
import com.company.model.form.BaseForm;
import org.springframework.http.ResponseEntity;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<?> signUp(RegisterRequest request);

    ResponseEntity<?> signIn(BaseForm baseForm);
}
