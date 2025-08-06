package com.blumbit.supermercado.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blumbit.supermercado.dto.request.MovimientoRequest;
import com.blumbit.supermercado.dto.request.NotaRequest;
import com.blumbit.supermercado.dto.response.nota.NotaResponse;
import com.blumbit.supermercado.entity.Almacen;
import com.blumbit.supermercado.entity.AlmacenProducto;
import com.blumbit.supermercado.entity.EntidadComercial;
import com.blumbit.supermercado.entity.Movimientos;
import com.blumbit.supermercado.entity.Notas;
import com.blumbit.supermercado.entity.Producto;
import com.blumbit.supermercado.entity.Usuario;
import com.blumbit.supermercado.repository.AlmacenProductoRepository;
import com.blumbit.supermercado.repository.MovimientoRepository;
import com.blumbit.supermercado.repository.NotaRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaService implements INotaService {
    private final NotaRepository notaRepository;
    private final MovimientoRepository movimientoRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public NotaResponse createNota(NotaRequest notaRequest) {
        try {
            // validate total
            BigDecimal totalCalculado = calculateTotal(notaRequest.getMovimientos());
            // if (totalCalculado != notaRequest.getTotalCalculado()) {
            //     throw new RuntimeException("Error al registrar la nota, los calculos no son correctos");
            // }
            // CREATE NOTA
            Notas notaToCreate = NotaRequest.toEntity(notaRequest);
            notaToCreate.setFechaEmision(LocalDate.now());
            notaToCreate.setCodigoNota(createCodigoNota());
            notaToCreate.setEstadoNota("REGISTRADO");
            notaToCreate.setEntidadComercial(
                    entityManager.getReference(EntidadComercial.class, notaRequest.getEntidadComercialId()));
            notaToCreate.setUsuario(entityManager.getReference(Usuario.class, notaRequest.getUsuarioId()));

            Notas notaCreated = notaRepository.save(notaToCreate);

            // CREATE MOVIMIENTOS
            // validar stock
            List<Movimientos> movimientosSaved = new ArrayList<>();
            for (MovimientoRequest movimientoRequest : notaRequest.getMovimientos()) {
                Movimientos movimientoToCreate = MovimientoRequest.toEntity(movimientoRequest);
                movimientoToCreate
                        .setAlmacen(entityManager.getReference(Almacen.class, movimientoRequest.getAlmacenId()));
                movimientoToCreate.setNotas(entityManager.getReference(Notas.class, notaCreated.getId()));
                movimientoToCreate
                        .setProducto(entityManager.getReference(Producto.class, movimientoRequest.getProductoId()));
                movimientosSaved.add(movimientoRepository.save(movimientoToCreate));
            }
            // UPDATE STOCK
            for (Movimientos movimiento : movimientosSaved) {
                AlmacenProducto almacenProductoRetrieved = almacenProductoRepository.findByAlmacen_IdAndProducto_Id(
                        movimiento.getAlmacen().getId(), movimiento.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("No se encontraron productos en almacen"));
                switch (movimiento.getTipoMovimiento()) {
                    case "0":
                        almacenProductoRetrieved.setCantidadActual(
                                almacenProductoRetrieved.getCantidadActual() + movimiento.getCantidad());
                        break;
                    case "1":
                        if (almacenProductoRetrieved.getCantidadActual() < movimiento.getCantidad()) {
                            throw new RuntimeException("No existe cantidad suficiente del producto "
                                    + movimiento.getProducto().getNombre());
                        }
                        almacenProductoRetrieved.setCantidadActual(
                                almacenProductoRetrieved.getCantidadActual() - movimiento.getCantidad());
                        break;
                    case "2":
                        // Devolucion asociado a ventas
                        almacenProductoRetrieved.setCantidadActual(
                                almacenProductoRetrieved.getCantidadActual() + movimiento.getCantidad());
                        break;
                }
                almacenProductoRepository.save(almacenProductoRetrieved);
            }
            // TODO create report nota;
            return NotaResponse.fromEntity(notaCreated);
        } catch (Exception e) {
            throw e;
        }

    }

    private BigDecimal calculateTotal(List<MovimientoRequest> movimientos) {
        BigDecimal totalCalculado = new BigDecimal(0);
        for (MovimientoRequest movimiento : movimientos) {
            totalCalculado = movimiento.getPrecioUnitarioVenta().add(new BigDecimal(movimiento.getCantidad()));
        }
        return totalCalculado;
    }

    private String createCodigoNota() {
        // N-0000001-2025
        int year = Instant.now().atZone(ZoneId.systemDefault()).getYear();
        long totalRecords = notaRepository.count() + 1;
        String codigoResult = String.format("%06d", totalRecords);
        return "N-" + codigoResult + "-" + year;
    }

    @Override
    public List<NotaResponse> findAllNotas() {
        return notaRepository.findAll().stream().map(NotaResponse::fromEntity).collect(Collectors.toList());
    }
}
