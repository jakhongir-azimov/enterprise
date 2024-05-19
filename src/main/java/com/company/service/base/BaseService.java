package com.company.service.base;

import com.company.model.dto.ResponseDto;
import com.company.model.dto.UserDto;
import com.company.model.form.UserForm;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class BaseService {


    public UserDto formToDto(UserForm userForm, Integer id) {
        return UserDto.builder()
                .id(id)
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .email(userForm.getEmail())
                .role(userForm.getRole())
                .build();
    }

    public ResponseDto<?> convertResponseDto(Object data, String message, Boolean success, int code) {

        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setSuccess(success);
        responseDto.setData(data);
        responseDto.setCode(code);
        responseDto.setMessage(message);
        return responseDto;

    }

    public String updateState(String email) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        email = email + timeStamp;

        return email;
    }
}
