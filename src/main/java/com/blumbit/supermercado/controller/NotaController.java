package com.blumbit.supermercado.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.dto.request.NotaRequest;
import com.blumbit.supermercado.dto.response.nota.NotaResponse;
import com.blumbit.supermercado.service.INotaService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/notas")
public class NotaController {

    private final INotaService notaService;

    public NotaController(INotaService notaService) {
        this.notaService = notaService;
    }

    @GetMapping
    public CustomResponse<List<NotaResponse>> getAllNotas() {
        return CustomResponse.<List<NotaResponse>>success(notaService.findAllNotas());
    }

    @PostMapping
    public CustomResponse<NotaResponse> postMethodName(@RequestBody NotaRequest notaRequest) {
        return CustomResponse.<NotaResponse>success(notaService.createNota(notaRequest));
    }
    
}
