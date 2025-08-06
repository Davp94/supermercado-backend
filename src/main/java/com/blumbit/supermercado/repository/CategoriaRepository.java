package com.blumbit.supermercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.supermercado.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
