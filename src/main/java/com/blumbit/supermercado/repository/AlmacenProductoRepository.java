package com.blumbit.supermercado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.supermercado.entity.AlmacenProducto;

public interface AlmacenProductoRepository extends ListCrudRepository<AlmacenProducto, Long>{
   
    Optional<AlmacenProducto> findByAlmacen_IdAndProducto_Id(Short idAlmacen, Long productoId);

    List<AlmacenProducto> findByAlmacen_Id(Short id);

}
