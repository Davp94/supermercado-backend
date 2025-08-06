package com.blumbit.supermercado.entity;

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
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombreCompleto;

    private String rolContacto;

    private String telefonoSecundario;

    private String correoSecundario;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "entidad_comercial_id")
    private EntidadComercial entidadComercial;
}
