package com.blumbit.supermercado.dto.response.nota;

import java.time.LocalDate;

import com.blumbit.supermercado.entity.Notas;

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
public class NotaResponse {

    private Long id;

    private String codigoNota;

    private LocalDate fechaEmision;

    private String tipoNota;

    private String estadoNota;

    private String observaciones;

    public static NotaResponse fromEntity (Notas nota){
        return NotaResponse.builder()
        .id(nota.getId())
        .codigoNota(nota.getCodigoNota())
        .estadoNota(nota.getEstadoNota())
        .fechaEmision(nota.getFechaEmision())
        .observaciones(nota.getObservaciones())
        .tipoNota(nota.getTipoNota())
        .build();
    }
}
