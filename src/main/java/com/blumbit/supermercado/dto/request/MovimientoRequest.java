package com.blumbit.supermercado.dto.request;

import java.math.BigDecimal;

import com.blumbit.supermercado.constant.enums.TipoMovimientoEnum;
import com.blumbit.supermercado.entity.Movimientos;
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
public class MovimientoRequest {
    private Short almacenId;
    private Long productoId;
    private Integer cantidad;
    private TipoMovimientoEnum tipoMovimiento;
    private BigDecimal precioUnitarioCompra;
    private BigDecimal precioUnitarioVenta;
    private BigDecimal totalLinea;
    private String observaciones;

    public static Movimientos toEntity(MovimientoRequest movimientoRequest){
        return Movimientos.builder()
                .observaciones(movimientoRequest.getObservaciones())
                .precioUnitarioCompra(movimientoRequest.getPrecioUnitarioCompra())
                .precioUnitarioVenta(movimientoRequest.getPrecioUnitarioVenta())
                .tipoMovimiento(String.valueOf(movimientoRequest.getTipoMovimiento().getValue()))
                .cantidad(movimientoRequest.getCantidad())
                .totalLinea(movimientoRequest.getTotalLinea()).build();
    }
}
