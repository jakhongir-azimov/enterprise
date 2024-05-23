package com.company.service;

import com.company.model.form.BaseForm;
import com.company.model.form.UserForm;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signUp(UserForm userForm);

    ResponseEntity<?> signIn(BaseForm baseForm);

    ResponseEntity<?> getUser(Integer id);

    ResponseEntity<?> listUsers(int page, int size);

    ResponseEntity<?> updateUser(Integer id, UserForm userForm);

    ResponseEntity<?> deleteUser(Integer id);
}
