package com.blumbit.supermercado.dto.request;

import java.math.BigDecimal;

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
public class ProductoRequest {

    private String nombre;
    
    private String descripcion;
    
    private String unidadMedida;
    
    private String marca;
    
    private BigDecimal precioVentaActual;
    
    private Integer stockMinimo;
    
    private String imagenUrl;
    
    private Long categoriaId;

    private Short almacenId;
}
