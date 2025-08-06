package com.blumbit.supermercado.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.Movimientos;

public interface MovimientoRepository extends ListCrudRepository<Movimientos, Long>{

}
