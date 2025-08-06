package com.blumbit.supermercado.service;

import java.util.List;

import com.blumbit.supermercado.dto.request.PermisoRequest;
import com.blumbit.supermercado.dto.request.RolRequest;
import com.blumbit.supermercado.dto.response.PermisoResponse;
import com.blumbit.supermercado.dto.response.RolResponse;

public interface IRolService {

    List<RolResponse> findAllRoles();

    RolResponse findRolById(Short id);

    RolResponse createRol(RolRequest rolRequest);

    RolResponse updateRol(Short id, RolRequest rolRequest);

    void deleteRol(Short id);

    PermisoResponse createPermiso(PermisoRequest permisoRequest);

    List<PermisoResponse> findAllPermisos();
    
}
