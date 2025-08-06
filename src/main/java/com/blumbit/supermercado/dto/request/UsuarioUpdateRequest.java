package com.blumbit.supermercado.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioUpdateRequest {

    private String email;

    private String telefono;

    private String direccion;

    private List<Short> rolesIds;
}
