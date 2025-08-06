package com.blumbit.supermercado.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.blumbit.supermercado.entity.Notas;
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
public class NotaRequest {
    private Long entidadComercialId;
    private Integer usuarioId;
    private String tipoNota;
    private BigDecimal subTotal;
    private BigDecimal descuentoTotal;
    private BigDecimal totalCalculado;
    private String observaciones;
    private List<MovimientoRequest> movimientos;

    public static Notas toEntity(NotaRequest notaRequest){
        return Notas.builder()
                .subtotal(notaRequest.getSubTotal())
                .descuentoTotal(notaRequest.getDescuentoTotal())
                .totalCalculado(notaRequest.getTotalCalculado())
                .observaciones(notaRequest.getObservaciones())
                .tipoNota(notaRequest.getTipoNota())
                .build();
    }
}
