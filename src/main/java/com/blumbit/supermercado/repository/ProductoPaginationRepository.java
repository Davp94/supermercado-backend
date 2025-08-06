package com.blumbit.supermercado.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.blumbit.supermercado.entity.Producto;

public interface ProductoPaginationRepository extends PagingAndSortingRepository<Producto, Long>, JpaSpecificationExecutor<Producto>{

    // @Query("select * from Producto")
    // Page<Producto> findAllWithFilters(Pageable pageable, String nombre, String codigoBarra);

}
