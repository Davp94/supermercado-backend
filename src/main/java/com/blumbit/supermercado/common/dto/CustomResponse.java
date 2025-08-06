package com.blumbit.supermercado.common.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse<T> {
    private HttpStatus statusCode;
    private boolean success;
    private String message;
    private T data;

    public static <T> CustomResponse<T> success(T data){
        return CustomResponse.<T>builder()
                .statusCode(HttpStatus.OK)
                .message("Operacion ejecutada exitosamente")
                .data(data)
                .success(Boolean.TRUE)
                .build();
    }
}
