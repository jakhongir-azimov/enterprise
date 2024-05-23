package com.company.service.impl;

import com.company.config.JwtService;
import com.company.enums.Role;
import com.company.mapper.UserMapper;
import com.company.model.dto.ResponseDto;
import com.company.model.dto.UserDto;
import com.company.model.entity.UserEntity;
import com.company.model.form.BaseForm;
import com.company.model.form.UserForm;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import com.company.service.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BaseService baseService;
    private final UserMapper userMapper;


    @Override
    public ResponseEntity<?> signUp(UserForm userForm) {

        ResponseDto<?> response;
        UserEntity user;

        try {
            System.out.println("UserForm: " + userForm);
            userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));

            user = userRepository.save(userMapper.UserFormToUserEntity(userForm));

            log.info("User sign-up successfully");
            response = baseService.convertResponseDto(baseService.formToDto(userForm, user.getId()), "success", true, 200);
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
            jwtToken = jwtService.generateToken(baseForm.getField());
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

    @Override
    public ResponseEntity<?> getUser(Integer id) {

        ResponseDto<?> response;

        response = baseService.convertResponseDto(userMapper.UserToUserDto(userRepository.findUserById(id)), "success", true, 200);

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<?> listUsers(int page, int size) {

        ResponseDto<?> response;

        Page<UserEntity> page1;
        try {
            PageRequest pageable = PageRequest.of(page, size);
            page1 = userRepository.findAllUsersWithStateTrue(pageable);

            List<UserDto> userDtos = page1.getContent().stream()
                    .map(userMapper::UserToUserDto)
                    .collect(Collectors.toList());

            log.info("Retrieved page {} of size {} from all users.", page, size);
            response = baseService.convertResponseDto(userDtos, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Error retrieving all users", e);
            response = baseService.convertResponseDto(null, "Interval Server Error", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(Integer id, UserForm userForm) {

        ResponseDto<?> response;

        try {
            userRepository.updateUser(userForm.getFirstName(),
                    userForm.getLastName(), userForm.getEmail(),
                    passwordEncoder.encode(userForm.getPassword()),
                    Role.valueOf(userForm.getRole()), id);
            log.info("User updated successfully");
            response = baseService.convertResponseDto(null, "User updated successfully", true, 200);
            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("User update failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer id) {

        ResponseDto<?> response;

        try {
            String newUsername = baseService.updateState(userRepository.findUserById(id).getEmail());

            userRepository.deleteUserById(newUsername, id);
            log.info("User deleted successfully");
            response = baseService.convertResponseDto(null, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Something went wrong while deleting user {}", id);
            response = baseService.convertResponseDto(null, "Something went wrong while deleting user", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }


}
