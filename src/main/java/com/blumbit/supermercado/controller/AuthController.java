package com.blumbit.supermercado.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.dto.request.AuthRequest;
import com.blumbit.supermercado.dto.response.AuthResponse;
import com.blumbit.supermercado.util.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public CustomResponse<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) { 
        return CustomResponse.success(authService.authenticate(authRequest));
    }

    @PostMapping("/refresh-token")
    //@PreAuthorize("hasAutority('CREATE_PRODUCT', 'READ_PRODUCT')")
    //@PreAuthorize("hasRole('ADMIN')")
    public CustomResponse<AuthResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication) {
        return CustomResponse.success(authService.refreshToken(authentication));
    }
}
