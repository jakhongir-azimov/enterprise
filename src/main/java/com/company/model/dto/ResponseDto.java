package com.company.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

}
