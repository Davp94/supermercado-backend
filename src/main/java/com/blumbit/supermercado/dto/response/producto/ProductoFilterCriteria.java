package com.blumbit.supermercado.dto.response.producto;

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
public class ProductoFilterCriteria {
    private Short almacenId;
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private String marca;
    private String nombreCategoria; 
}
