package com.blumbit.supermercado.dto.response.producto;

import java.math.BigDecimal;

import com.blumbit.supermercado.entity.Producto;
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
public class ProductoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private String marca;
    private String nombreCategoria;
    private BigDecimal precioVentaActual;


    public static ProductoResponse fromEntity(Producto producto){
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .codigoBarra(producto.getCodigoBarra())
                .marca(producto.getMarca())
                .nombreCategoria(producto.getCategoria().getNombre())
                .precioVentaActual(producto.getPrecioVentaActual())
                .build();
    }

}
