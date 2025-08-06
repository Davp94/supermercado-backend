package com.blumbit.supermercado.dto.request;

import com.blumbit.supermercado.entity.Permiso;
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
public class PermisoRequest {
    private String nombre;

    private String descripcion;

    private String action;

    public static Permiso toEntity(PermisoRequest permisoRequest){
        return Permiso.builder()
        .action(permisoRequest.getAction())
        .descripcion(permisoRequest.getDescripcion())
        .nombre(permisoRequest.getNombre())
        .build();
    }
}
