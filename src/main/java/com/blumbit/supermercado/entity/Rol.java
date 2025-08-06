package com.blumbit.supermercado.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rol {

    @Id
    @GeneratedValue
    private Short id;

    private String nombre;

    private String descripcion;

    private Short estado;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

}
