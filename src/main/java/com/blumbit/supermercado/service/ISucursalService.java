package com.blumbit.supermercado.service;

import java.util.List;

import com.blumbit.supermercado.dto.response.AlmacenResponse;
import com.blumbit.supermercado.dto.response.SucursalResponse;

public interface ISucursalService {

    List<SucursalResponse> getAllSucursales();

    List<AlmacenResponse> getAlmacenesBySucursalId(Short sucursalId);

}
