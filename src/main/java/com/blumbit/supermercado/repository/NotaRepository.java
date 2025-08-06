package com.blumbit.supermercado.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.Notas;

public interface NotaRepository extends ListCrudRepository<Notas, Long>{

}
