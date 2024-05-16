package com.company.service.impl;

import com.company.auth.AuthenticationRequest;
import com.company.auth.AuthenticationResponse;
import com.company.auth.RegisterRequest;
import com.company.config.JwtService;
import com.company.model.dto.ResponseDto;
import com.company.model.entity.UserEntity;
import com.company.model.form.BaseForm;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import com.company.service.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final BaseService baseService;


    public AuthenticationResponse register(RegisterRequest request) {

//        var user = UserEntity.builder()
//                .firstName(request.getFirstname())
//                .lastName(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole())
//                .build();
//        userRepository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
        return null;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(jwtToken)
//                .build();
        return null;
    }

    @Override
    public ResponseEntity<?> signUp(RegisterRequest request) {

        ResponseDto<?> response;

        try {
            var user = UserEntity.builder()
                    .firstName(request.getFirstname())
                    .lastName(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();
            userRepository.save(user);

            log.info("User sign-up successfully");
            response = baseService.convertResponseDto(user, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Something went wrong during creation");
            response = baseService.convertResponseDto(null, "Something went wrong during creation", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> signIn(BaseForm baseForm) {

        ResponseDto<?> response;

        String jwtToken = "";
        String userEmail = "";
        String role = "";

        Optional<UserEntity> email =
                userRepository.findByEmail(baseForm.getField());

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        baseForm.getField(),
                        baseForm.getObject())
        );

        if (email.isEmpty()) {
            log.warn("There is no user with this email");
            response = baseService.convertResponseDto(null, "Email or password wrong", false, 500);
            return ResponseEntity.status(500).body(response);
        }

        if (!email.get().getState()) {
            log.info("User has been deleted");
            response = baseService.convertResponseDto(null, "User is deleted", false, 403);
            return ResponseEntity.status(403).body(response);
        }

        if (!auth.isAuthenticated()) {
            log.warn("There is no user with this username");
            response = baseService.convertResponseDto(null, "Username or password wrong", false, 500);
            return ResponseEntity.status(500).body(response);
        } else {
            jwtToken= jwtService.generateToken(baseForm.getField());
            userEmail = email.get().getUsername();
            role = email.get().getRole().toString();
        }

        log.info("logged success");
        Map<String, String> map = new HashMap<>();
        map.put("jwtToken", jwtToken);
        map.put("email", userEmail);
        map.put("role", role);

        response = baseService.convertResponseDto(map, "success", true, 200);
        return ResponseEntity.status(200).body(response);
    }


}
