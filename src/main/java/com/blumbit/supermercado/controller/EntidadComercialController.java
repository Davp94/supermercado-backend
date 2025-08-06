package com.blumbit.supermercado.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.entity.EntidadComercial;
import com.blumbit.supermercado.repository.EntidadComercialRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/entidad-comercial")
@RequiredArgsConstructor
public class EntidadComercialController {

    private final EntidadComercialRepository entidadComercialRepository;

    @GetMapping
    public CustomResponse<List<EntidadComercial>> getEntidadesComerciales() {
        return CustomResponse.success(entidadComercialRepository.findAll());
    }
    
    
}
