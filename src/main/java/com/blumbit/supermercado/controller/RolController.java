package com.blumbit.supermercado.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.dto.request.PermisoRequest;
import com.blumbit.supermercado.dto.request.RolRequest;
import com.blumbit.supermercado.dto.response.PermisoResponse;
import com.blumbit.supermercado.dto.response.RolResponse;
import com.blumbit.supermercado.service.IRolService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    private final IRolService rolService;

    @GetMapping
    public CustomResponse<List<RolResponse>> getRoles() {
        try {
            return CustomResponse.success(rolService.findAllRoles());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public CustomResponse<RolResponse> getRoles(@PathVariable Short id) {
        try {
            return CustomResponse.success(rolService.findRolById(id));
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public CustomResponse<RolResponse> createRol(@RequestBody RolRequest rolRequest) {
        try {
            return CustomResponse.success(rolService.createRol(rolRequest));
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public CustomResponse<RolResponse> updateRol(@PathVariable Short id, @RequestBody RolRequest rolRequest) {
        try {
            return CustomResponse.success(rolService.updateRol(id, rolRequest));
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/permisos")
    public CustomResponse<List<PermisoResponse>> getPermisos() {
        try {
            return CustomResponse.success(rolService.findAllPermisos());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/permiso")
    public CustomResponse<PermisoResponse> createPermiso(@RequestBody PermisoRequest permisoRequest) {
        try {
            return CustomResponse.success(rolService.createPermiso(permisoRequest));
        } catch (Exception e) {
            throw e;
        }
    }
    
}
