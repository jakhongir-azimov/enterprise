package com.company.service.base;

import com.company.model.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    public ResponseDto<?> convertResponseDto(Object data, String message, Boolean success, int code){

        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setSuccess(success);
        responseDto.setData(data);
        responseDto.setCode(code);
        responseDto.setMessage(message);
        return responseDto;

    }

}
