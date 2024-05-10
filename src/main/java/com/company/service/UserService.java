package com.company.service;

import com.company.auth.AuthenticationRequest;
import com.company.auth.AuthenticationResponse;
import com.company.auth.RegisterRequest;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
