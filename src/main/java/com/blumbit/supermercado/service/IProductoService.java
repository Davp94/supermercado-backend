package com.blumbit.supermercado.service;

import java.util.List;

import com.blumbit.supermercado.common.dto.PageableRequest;
import com.blumbit.supermercado.common.dto.PageableResponse;
import com.blumbit.supermercado.dto.request.ProductoAlmacenRequest;
import com.blumbit.supermercado.dto.request.ProductoRequest;
import com.blumbit.supermercado.dto.response.producto.ProductoFilterCriteria;
import com.blumbit.supermercado.dto.response.producto.ProductoResponse;

public interface IProductoService {
    PageableResponse<ProductoResponse> getProductsPagination(PageableRequest<ProductoFilterCriteria> pageableRequest);

    ProductoResponse createProducto(ProductoRequest productoRequest);

    ProductoResponse createProductoAlmacen(ProductoAlmacenRequest productoAlmacenRequest);

    List<ProductoResponse> getAllProductosByAlmacen(Short almacenId);
}
