package com.blumbit.supermercado.controller;

import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.common.dto.PageableRequest;
import com.blumbit.supermercado.common.dto.PageableResponse;
import com.blumbit.supermercado.dto.request.ProductoAlmacenRequest;
import com.blumbit.supermercado.dto.response.producto.ProductoFilterCriteria;
import com.blumbit.supermercado.dto.response.producto.ProductoResponse;
import com.blumbit.supermercado.service.IProductoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/pagination")
    public CustomResponse<PageableResponse<ProductoResponse>> getMethodName(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(required = false) String filterValue,
            @RequestParam(required = false) Short almacenId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String codigoBarra,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String nombreCategoria) {
        ProductoFilterCriteria criteria = ProductoFilterCriteria.builder()
                .almacenId(almacenId)
                .nombre(nombre)
                .descripcion(descripcion)
                .codigoBarra(codigoBarra)
                .marca(marca)
                .nombreCategoria(nombreCategoria)
                .build();

        PageableRequest<ProductoFilterCriteria> request = PageableRequest.<ProductoFilterCriteria>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .filterValue(filterValue)
                .criterials(criteria)
                .build();
        return CustomResponse.success(productoService.getProductsPagination(request));
    }

    @GetMapping("/almacen/{id}")
    public CustomResponse<List<ProductoResponse>> getProductosByAlmacen(@PathVariable Short id) {
        return CustomResponse.success(productoService.getAllProductosByAlmacen(id));
    }
    

    @PostMapping("/almacen")
    public CustomResponse<ProductoResponse> addProductoAlmacen(
            @RequestBody ProductoAlmacenRequest productoAlmacenRequest) {
        return CustomResponse.success(productoService.createProductoAlmacen(productoAlmacenRequest));
    }
}
