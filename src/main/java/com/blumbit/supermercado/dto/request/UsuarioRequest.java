package com.blumbit.supermercado.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.blumbit.supermercado.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UsuarioRequest {

    @NotBlank(message = "El campo no puede estar vacio")
    @Email(message = "El campo debe ser un correo valido")
    @NotNull(message = "El campo no puede ser nulo")
    private String email;

    private String fechaNacimiento;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    @Pattern(regexp = "")
    private String nombres;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    @Pattern(regexp = "")
    private String apellidos;

    @NotBlank(message = "El campo no puede estar vacio")
    private String genero;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    private String telefono;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    @Size(min = 30, max = 200, message = "El campo debe estar entre {min} y {max} caracteres")
    private String direccion;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    private String dni;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    private String tipoDocumento;

    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo no puede ser nulo")
    private String nacionalidad;

    private List<Short> rolesIds;

    public static Usuario toEntity(UsuarioRequest usuarioRequest){
        return Usuario.builder()
                .email(usuarioRequest.getEmail())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento() != null ? LocalDate.parse(usuarioRequest.getFechaNacimiento()) : null)
                .nombres(usuarioRequest.getNombres())
                .apellidos(usuarioRequest.getApellidos())
                .genero(usuarioRequest.getGenero())
                .telefono(usuarioRequest.getTelefono())
                .direccion(usuarioRequest.getDireccion())
                .documentoIdentidad(usuarioRequest.getDni())
                .tipoDocumento(usuarioRequest.getTipoDocumento())
                .nacionalidad(usuarioRequest.getNacionalidad())
                .build();
    }

}
