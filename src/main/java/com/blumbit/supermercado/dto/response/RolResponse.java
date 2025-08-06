package com.blumbit.supermercado.dto.response;

import java.util.List;

import com.blumbit.supermercado.entity.Rol;
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
public class RolResponse {

    private Short id;

    private String nombre;

    private String descripcion;

    private List<Integer> permisosIds;

    public static RolResponse fromEntityToList(Rol rol){
        return RolResponse.builder()
            .id(rol.getId())
            .nombre(rol.getNombre())
            .descripcion(rol.getDescripcion())
            .build();
    }

    public static RolResponse fromEntity(Rol rol){
        return RolResponse.builder()
            .id(rol.getId())
            .nombre(rol.getNombre())
            .descripcion(rol.getDescripcion())
            .build();
    }
}
