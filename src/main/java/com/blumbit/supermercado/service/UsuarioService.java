package com.blumbit.supermercado.service;

import com.blumbit.supermercado.dto.request.UsuarioRequest;
import com.blumbit.supermercado.dto.request.UsuarioUpdateRequest;
import com.blumbit.supermercado.dto.response.UsuarioResponse;
import com.blumbit.supermercado.entity.Rol;
import com.blumbit.supermercado.entity.Usuario;
import com.blumbit.supermercado.exception.NotFoundByIdException;
import com.blumbit.supermercado.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UsuarioService implements IUsuarioService{
    private final UsuarioRepository usuarioRepository;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UsuarioResponse findById(Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id).orElse(null);
            if (usuario == null) {
                throw new NotFoundByIdException("No se encontro un usuario con el id ingresado");
            }
            return UsuarioResponse.fromEntity(usuario);
        } catch (Exception e) {
           throw e;
        }
    }

    @Override
    public List<UsuarioResponse> findAll() {
        try {
            //FORMA TRADICIONAL
            //  List<UsuarioResponse> usuariosResponse = new ArrayList();
            //  List<Usuario> usuarios = usuarioRepository.findAll();
            //  for (Usuario usuario : usuarios) {
            //     if(usuario.getNacionalidad().equals("Boliviano")){
            //         usuariosResponse.add(UsuarioResponse.fromEntity(usuario));
            //     }     
            //  }
            //  return usuariosResponse;
            //CON LAMBDAS Y STREAMS
            List<UsuarioResponse> response = usuarioRepository.findAll().stream()
                    .map(UsuarioResponse::fromEntity).collect(Collectors.toList());
            if(response.size() == 0){
                throw new RuntimeException("No existen registros de usuarios");
            }
            return response;
        } catch (Exception e) {
             throw e;
        }  
    }

    @Override
    public UsuarioResponse save(UsuarioRequest usuario) {
        try {
            Usuario usuarioToSave = UsuarioRequest.toEntity(usuario);
            usuarioToSave.setPassword(passwordEncoder.encode("123456"));
            usuarioToSave.setRoles(usuario.getRolesIds().stream().map(rolId->
                entityManager.getReference(Rol.class, rolId)
            ).collect(Collectors.toList()));
            return UsuarioResponse.fromEntity(usuarioRepository.save(usuarioToSave));
        } catch (Exception e) {
            log.error("EXCEPTION", e);
           throw new RuntimeException("error creando usuario");
        }
    }

    @Override
    public UsuarioResponse update(Long id, UsuarioUpdateRequest usuario) {
        try {
            Usuario usuarioRetrieved = usuarioRepository.findById(id).orElse(null);
            if (usuario == null) {
                throw new RuntimeException("No se encontro un usuario con el id ingresado");
            }
            if(usuario.getRolesIds() != null){
            usuarioRetrieved.setRoles(usuario.getRolesIds().stream().map(rolId->
                entityManager.getReference(Rol.class, rolId)
            ).collect(Collectors.toList()));
            }
            if(usuario.getEmail() != null){
                usuarioRetrieved.setEmail(usuario.getEmail());
            }
            if(usuario.getDireccion() != null){
                usuarioRetrieved.setDireccion(usuario.getDireccion());
            }
            if(usuario.getTelefono() != null){
                usuarioRetrieved.setTelefono(usuario.getTelefono());
            }

            return UsuarioResponse.fromEntity(usuarioRepository.save(usuarioRetrieved));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario");
        }
    }

}
