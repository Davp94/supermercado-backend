package com.blumbit.supermercado.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(unique = true, length = 100)
    private String codigoBarra;
    
    @Column(nullable = false, length = 50)
    private String unidadMedida;
    
    @Column(length = 100)
    private String marca;
    
    @Column(name = "precio_venta_actual", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioVentaActual;
    
    @Column(name = "stock_minimo")
    private Integer stockMinimo;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(nullable = false)
    private Boolean activo;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    // @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    // private Set<AlmacenProducto> almacenProductos = new HashSet<>();
    
    // @OneToMany(mappedBy = "producto")
    // private Set<Movimiento> movimientos = new HashSet<>();
    
    // Constructors, getters, setters
}
