package com.blumbit.supermercado.dto.response;

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
public class PermisoResponse {

    private Integer id;

    private String nombre;

    private String descripcion;

    private String action;

    public static PermisoResponse fromEntity(Permiso permiso){
        return PermisoResponse.builder()
            .id(permiso.getId())
            .nombre(permiso.getNombre())
            .descripcion(permiso.getDescripcion())
            .action(permiso.getAction())
            .build();
    }

}
