package com.blumbit.supermercado.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.Rol;

public interface RolRepository extends ListCrudRepository<Rol, Short>{

    List<Rol> findAllByEstado(Short estado);

}
