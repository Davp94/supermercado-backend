package com.blumbit.supermercado.dto.response;

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
public class AlmacenResponse {

    private Short id;

    private String nombre;

    private String codigo;

    private String descripcion;

    private Short sucursalId;

}
