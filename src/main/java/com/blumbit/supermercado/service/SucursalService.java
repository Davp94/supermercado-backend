package com.blumbit.supermercado.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blumbit.supermercado.dto.response.AlmacenResponse;
import com.blumbit.supermercado.dto.response.SucursalResponse;
import com.blumbit.supermercado.repository.AlmacenRepository;
import com.blumbit.supermercado.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService implements ISucursalService {

    private final SucursalRepository sucursalRepository;
    private final AlmacenRepository almacenRepository;
    
    @Override
    public List<SucursalResponse> getAllSucursales() {

        return sucursalRepository.findAll()
                .stream()
                .map(sucursal -> SucursalResponse.builder()
                        .id(sucursal.getId())
                        .nombre(sucursal.getNombre())
                        .descripcion(sucursal.getDescripcion())
                        .telefono(sucursal.getTelefono())
                        .ciudad(sucursal.getCiudad())
                        .build())
                .toList();
    }

    @Override
    public List<AlmacenResponse> getAlmacenesBySucursalId(Short sucursalId) {
        return almacenRepository.findBySucursalId(sucursalId)   
                .stream()
                .map(almacen -> AlmacenResponse.builder()
                        .id(almacen.getId())
                        .nombre(almacen.getNombre())
                        .codigo(almacen.getCodigo())
                        .descripcion(almacen.getDescripcion())
                        .sucursalId(almacen.getSucursal().getId())
                        .build())
                .toList();
    }

}
