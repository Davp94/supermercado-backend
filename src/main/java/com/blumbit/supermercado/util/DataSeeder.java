package com.blumbit.supermercado.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.supermercado.entity.Almacen;
import com.blumbit.supermercado.entity.AlmacenProducto;
import com.blumbit.supermercado.entity.Categoria;
import com.blumbit.supermercado.entity.EntidadComercial;
import com.blumbit.supermercado.entity.Permiso;
import com.blumbit.supermercado.entity.PermisoRol;
import com.blumbit.supermercado.entity.Producto;
import com.blumbit.supermercado.entity.Rol;
import com.blumbit.supermercado.entity.Sucursal;
import com.blumbit.supermercado.entity.Usuario;
import com.blumbit.supermercado.repository.*;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final PermisoRolRepository permisoRolRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final SucursalRepository sucursalRepository;
    private final AlmacenRepository almacenRepository;
    private final EntidadComercialRepository entidadComercialRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Rol rolAdmin = rolRepository.save(Rol.builder()
                    .nombre("ADMIN")
                    .descripcion("Rol administrador del sistema")
                    .build());
            Usuario usuarioSaved = usuarioRepository.save(Usuario.builder()
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("Admin1!"))
                    .nombres("admin")
                    .roles(List.of(rolAdmin))
                    .build());
            Permiso permiso1 = Permiso.builder()
                    .descripcion("administracion de usuarios")
                    .nombre("USUARIOS")
                    .action("ADMIN")
                    .build();
            Permiso permiso2 = Permiso.builder()
                    .descripcion("administracion de productos")
                    .nombre("PRODUCTOS")
                    .action("ADMIN")
                    .build();
            List<Permiso> permisosSaved = permisoRepository.saveAll(List.of(permiso1, permiso2));
            PermisoRol permisoRol1 = PermisoRol.builder()
                    .permiso(permisosSaved.getFirst())
                    .rol(rolAdmin)
                    .active(true)
                    .fechaCreacion(Instant.now())
                    .build();
            PermisoRol permisoRol2 = PermisoRol.builder()
                    .permiso(permisosSaved.getLast())
                    .rol(rolAdmin)
                    .active(true)
                    .fechaCreacion(Instant.now())
                    .build();
            permisoRolRepository.saveAll(List.of(permisoRol1, permisoRol2));
        }
        // TODO ADD DATA PRODUCTOS & CATEGORIAS
        Faker faker = new Faker();
        Random random = new Random();
        if (categoriaRepository.count() == 0) {
            List<Categoria> categorias = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Categoria categoriaSaved = categoriaRepository.save(Categoria.builder()
                        .descripcion(faker.lorem().sentence(10))
                        .nombre(faker.commerce().department())
                        .build());
                categorias.add(categoriaSaved);
            }

            for (int i = 0; i < 1000; i++) {
                Categoria categoria = categorias.get(random.nextInt(categorias.size()));
                productoRepository.save(Producto.builder()
                        .activo(faker.bool().bool())
                        .codigoBarra(faker.code().ean13())
                        .descripcion(faker.commerce().productName() + " - " + faker.lorem().sentence(5))
                        .fechaRegistro(LocalDate.now())
                        // .fechaRegistro(faker.date().between(
                        // Date.from(LocalDate.now().minusYears(2).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        // Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())))
                        .imagenUrl("image-product-example.png")
                        .marca(faker.company().name())
                        .nombre(faker.commerce().productName())
                        .precioVentaActual(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000)))
                        .stockMinimo(faker.number().numberBetween(5, 50))
                        .categoria(categoria)
                        .unidadMedida(getRandomUnidadMedida(faker)).build());
            }
        }
        if (sucursalRepository.count() == 0 && almacenRepository.count() == 0) {
            // CREATE SUCURSALES
            List<Sucursal> sucursales = new ArrayList<>();
            String[] ciudades = { "Santa Cruz", "La Paz", "Cochabamba", "Tarija", "Potosí" };

            for (int i = 0; i < 5; i++) {
                Sucursal sucursal = Sucursal.builder()
                        .nombre("Sucursal " + faker.company().name())
                        .descripcion(faker.lorem().sentence(8))
                        .telefono(faker.phoneNumber().cellPhone())
                        .ciudad(ciudades[i])
                        .build();

                Sucursal sucursalSaved = sucursalRepository.save(sucursal);
                sucursales.add(sucursalSaved);
            }
            // Create 10 Almacenes (2 per sucursal)
            List<Almacen> almacenes = new ArrayList<>();
            String[] tiposAlmacen = { "Principal", "Secundario", "Temporal", "Especial", "General" };

            for (int i = 0; i < 10; i++) {
                // Distribute almacenes evenly among sucursales (2 per sucursal)
                Sucursal sucursal = sucursales.get(i / 2);

                String codigo = "ALM-" + String.format("%03d", i + 1);
                String tipoAlmacen = tiposAlmacen[random.nextInt(tiposAlmacen.length)];

                Almacen almacen = Almacen.builder()
                        .nombre("Almacén " + tipoAlmacen + " " + sucursal.getCiudad())
                        .codigo(codigo)
                        .descripcion(faker.lorem().sentence(6) + " ubicado en " + sucursal.getCiudad())
                        .sucursal(sucursal)
                        .build();

                Almacen almacenSaved = almacenRepository.save(almacen);
                almacenes.add(almacenSaved);
            }
            // Create AlmacenProducto relations
            // Get all existing products
            List<Producto> productos = productoRepository.findAll();
            if (!productos.isEmpty()) {
                for (Almacen almacen : almacenes) {
                    for (int i = 0; i < 200; i++) {
                        Producto producto = productos.get(random.nextInt(productos.size()));

                        // Create AlmacenProducto relation
                        AlmacenProducto almacenProducto = AlmacenProducto.builder()
                                .cantidadActual(faker.number().numberBetween(0, 500))
                                .fechaActualizacion(LocalDate.now().minusDays(faker.number().numberBetween(0, 90)))
                                .almacen(almacen)
                                .producto(producto)
                                .build();

                        almacenProductoRepository.save(almacenProducto);
                    }
                }
            }
        }

        if (entidadComercialRepository.count() <= 0) {
            String[] tiposComerciales = {
                    "EMPRESA", "COOPERATIVA", "FUNDACION", "ONG",
                    "ASOCIACION", "CORPORACION", "SOCIEDAD", "MICROEMPRESA"
            };
            for (int i = 0; i < 10; i++) {

                EntidadComercial entidadComercial = EntidadComercial.builder()
                        .tipo(tiposComerciales[random.nextInt(tiposComerciales.length)])
                        .razonSocial(faker.company().name() + " " +
                                faker.options().option("S.A.", "S.R.L.", "LTDA", "CIA"))
                        .ciNitRucRut(generateBusinessId(faker, random))
                        .telefono(generatePhoneNumber(faker))
                        .direccion(faker.address().streetAddress() + ", " +
                                faker.address().city())
                        .correo(faker.internet().emailAddress())
                        .activo(random.nextBoolean() ? true : random.nextDouble() > 0.2)
                        .build();

                entidadComercialRepository.save(entidadComercial);
            }
        }
    }

    private String getRandomUnidadMedida(Faker faker) {
        String[] unidades = { "kg", "g", "ml", "l", "pack", "unit" };
        return unidades[faker.number().numberBetween(0, unidades.length)];
    }

    private String generateBusinessId(Faker faker, Random random) {
        String[] prefixes = { "NIT-", "RUC-", "RUT-", "CI-" };
        String prefix = prefixes[random.nextInt(prefixes.length)];
        long number = faker.number().numberBetween(1000000L, 99999999L);

        return prefix + number;
    }

    private String generatePhoneNumber(Faker faker) {
        String[] formats = {
                faker.phoneNumber().cellPhone(),
                faker.phoneNumber().phoneNumber(),
                "+591 " + faker.number().digits(8), // Bolivia format
                faker.number().digits(7) + "-" + faker.number().digits(4)
        };

        Random random = new Random();
        return formats[random.nextInt(formats.length)];
    }

}
