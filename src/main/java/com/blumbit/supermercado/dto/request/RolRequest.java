package com.blumbit.supermercado.dto.request;

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
public class RolRequest {
    
    private String nombre;

    private String descripcion;

    private List<Integer> permisosIds;

    public static Rol toEntity(RolRequest rolRequest){
        return Rol.builder()
            .descripcion(rolRequest.getDescripcion())
            .nombre(rolRequest.getNombre())
            .estado((short)1)
            .build();
    }
}
