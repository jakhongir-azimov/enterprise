package com.company.controller;

import com.company.model.form.BaseForm;
import com.company.model.form.UserForm;
import com.company.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "Users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody UserForm userForm
    ) {
        log.info("Received create user request");
        return userService.signUp(userForm);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(
            @RequestBody BaseForm baseForm
    ) {
        log.info("Received sign-in user request");
        return userService.signIn(baseForm);
    }

    @Operation(
            description = "Get user",
            summary = "get endpoint of users"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable("id") int id
    ) {
        log.info("Get user by id {}", id);
        return userService.getUser(id);
    }

    @Operation(
            description = "Get list users"
    )
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Received request to retrieve page {} of size {} from all users.", page, size);
        return userService.listUsers(page, size);
    }

    @Operation(
            description = "Update user",
            summary = "update endpoint of users"
    )
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") int id,
            @Valid @RequestBody UserForm userForm
    ) {
        log.info("User updating with id {}", id);
        return userService.updateUser(id, userForm);
    }

    @Operation(
            description = "Delete users",
            summary = "delete endpoint of users"
    )
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("id") int id
    ) {
        log.info("User deleting by id {}", id);
        return userService.deleteUser(id);
    }

}
