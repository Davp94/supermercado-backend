package com.blumbit.supermercado.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.Usuario;



public interface UsuarioRepository extends ListCrudRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);

    // @Query("SELECT u FROM Usuario u WHERE u.genero = 'FEMENINO'", nativeQuery = true)
    // List<Usuario> findUsuariosCustom();
}

    
