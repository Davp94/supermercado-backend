package com.blumbit.supermercado.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.blumbit.supermercado.common.dto.PageableRequest;
import com.blumbit.supermercado.common.dto.PageableResponse;
import com.blumbit.supermercado.dto.request.ProductoAlmacenRequest;
import com.blumbit.supermercado.dto.request.ProductoRequest;
import com.blumbit.supermercado.dto.response.producto.ProductoFilterCriteria;
import com.blumbit.supermercado.dto.response.producto.ProductoResponse;
import com.blumbit.supermercado.entity.Almacen;
import com.blumbit.supermercado.entity.AlmacenProducto;
import com.blumbit.supermercado.entity.Producto;
import com.blumbit.supermercado.repository.AlmacenProductoRepository;
import com.blumbit.supermercado.repository.AlmacenRepository;
import com.blumbit.supermercado.repository.ProductoPaginationRepository;
import com.blumbit.supermercado.repository.ProductoRepository;
import com.blumbit.supermercado.repository.specification.ProductoSpecifications;

@Service
public class ProductoService implements IProductoService {

    private final ProductoPaginationRepository productoPaginationRepository;

    private final ProductoRepository productoRepository;

    private final AlmacenProductoRepository almacenProductoRepository;

    private final AlmacenRepository almacenRepository;

    public ProductoService(ProductoPaginationRepository productoPaginationRepository,
            ProductoRepository productoRepository, AlmacenProductoRepository almacenProductoRepository,
            AlmacenRepository almacenRepository) {
        this.productoPaginationRepository = productoPaginationRepository;
        this.productoRepository = productoRepository;
        this.almacenProductoRepository = almacenProductoRepository;
        this.almacenRepository = almacenRepository;
    }

    @Override
    public PageableResponse<ProductoResponse> getProductsPagination(
            PageableRequest<ProductoFilterCriteria> pageableRequest) {

        Sort sort = pageableRequest.getSortOrder().equalsIgnoreCase("desc")
                ? Sort.by(pageableRequest.getSortField()).descending()
                : Sort.by(pageableRequest.getSortField()).ascending();
        Pageable pageable = PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), sort);
        Specification<Producto> spec = Specification.where(null);
        if (pageableRequest.getCriterials() != null) {
            spec = ProductoSpecifications.createSpecification(pageableRequest.getCriterials());
        }
        Page<Producto> productPage = productoPaginationRepository.findAll(spec, pageable);

        return PageableResponse.<ProductoResponse>builder()
                .pageNumber(productPage.getNumber())
                .totalElements(productPage.getTotalElements())
                .pageSize(productPage.getSize())
                .totalPages(productPage.getTotalPages())
                .content(productPage.getContent().stream().map(ProductoResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ProductoResponse createProducto(ProductoRequest productoRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProducto'");
    }

    @Override
    public ProductoResponse createProductoAlmacen(ProductoAlmacenRequest productoAlmacenRequest) {
        try {
            AlmacenProducto almacenProductoRetrieved = almacenProductoRepository.findByAlmacen_IdAndProducto_Id(
                    productoAlmacenRequest.getAlmacenId(), productoAlmacenRequest.getProductoId())
                    .orElse(null);
            Producto producto = productoRepository.findById(productoAlmacenRequest.getProductoId())
                    .orElseThrow(() -> new RuntimeException("El producto no existe"));
            if (almacenProductoRetrieved == null) {
                Almacen almacen = almacenRepository.findById(productoAlmacenRequest.getAlmacenId())
                        .orElseThrow(() -> new RuntimeException("El almacen no existe"));
                almacenProductoRepository.save(AlmacenProducto.builder().almacen(almacen).producto(producto).build());
            }

            return ProductoResponse.fromEntity(producto);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<ProductoResponse> getAllProductosByAlmacen(Short almacenId) {
        return almacenProductoRepository.findByAlmacen_Id(almacenId).stream().map(res -> ProductoResponse.fromEntity(res.getProducto())).toList();
    }

}
