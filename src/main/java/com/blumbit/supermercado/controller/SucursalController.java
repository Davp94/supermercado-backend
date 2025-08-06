package com.blumbit.supermercado.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.dto.response.AlmacenResponse;
import com.blumbit.supermercado.dto.response.SucursalResponse;
import com.blumbit.supermercado.service.ISucursalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final ISucursalService sucursalService;

    @GetMapping
    public CustomResponse<List<SucursalResponse>> getSucursales() {
        try {
            return CustomResponse.success(sucursalService.getAllSucursales());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/almacenes/{sucursalId}")
    public CustomResponse<List<AlmacenResponse>> getAlmacenes(@PathVariable Short sucursalId) {
        try {
            return CustomResponse.success(sucursalService.getAlmacenesBySucursalId(sucursalId));
        } catch (Exception e) {
            throw e;
        }
    }
}
