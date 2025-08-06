package com.blumbit.supermercado.service;

import java.util.List;

import com.blumbit.supermercado.dto.request.NotaRequest;
import com.blumbit.supermercado.dto.response.nota.NotaResponse;

public interface INotaService {

    NotaResponse createNota(NotaRequest notaRequest);

    List<NotaResponse> findAllNotas();
}
