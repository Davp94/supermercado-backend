package com.blumbit.supermercado.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.EntidadComercial;

public interface EntidadComercialRepository extends ListCrudRepository<EntidadComercial, Long>{
    
}
