package com.blumbit.supermercado.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String codigoNota;

    private LocalDate fechaEmision;

    private String tipoNota;
    
    private BigDecimal subtotal;
    
    private BigDecimal impuestos;

    private BigDecimal descuentoTotal;

    private BigDecimal totalCalculado;

    private String estadoNota;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "entidad_comercial_id")
    private EntidadComercial entidadComercial;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
