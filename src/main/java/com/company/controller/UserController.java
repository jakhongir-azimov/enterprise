package com.company.controller;

import com.company.auth.RegisterRequest;
import com.company.model.form.BaseForm;
import com.company.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "Users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(
            @RequestBody RegisterRequest request
    ) {
        System.out.println("working");
        log.info("Received create user request");
        return ResponseEntity.ok(userService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(
            @RequestBody BaseForm baseForm
    ) {
        log.info("Received sign-in user request");
        return ResponseEntity.ok(userService.signIn(baseForm));
    }


}
