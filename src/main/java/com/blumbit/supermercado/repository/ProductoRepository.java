package com.blumbit.supermercado.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.Producto;

public interface ProductoRepository extends ListCrudRepository<Producto, Long>{

}
