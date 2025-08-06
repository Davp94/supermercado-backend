package com.blumbit.supermercado.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.PermisoRol;

public interface PermisoRolRepository extends ListCrudRepository<PermisoRol, Integer>{

    List<PermisoRol> findByRol_Id(Short id);

}
