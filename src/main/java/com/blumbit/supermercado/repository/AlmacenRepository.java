package com.blumbit.supermercado.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import com.blumbit.supermercado.entity.Almacen;

public interface AlmacenRepository extends ListCrudRepository<Almacen, Short>{

    List<Almacen> findBySucursalId(Short sucursalId);

}
