package com.Productos.controllers;

import com.Productos.dto.ProductosDTO;
import com.Productos.services.ProductosService;
import com.Productos.assembler.ProductosAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    @Autowired
    private ProductosService service;

    @Autowired
    private ProductosAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<ProductosDTO>> crear(@RequestBody ProductosDTO dto) {
        ProductosDTO creado = service.crear(dto);
        return ResponseEntity
            .created(linkTo(methodOn(ProductosController.class).obtener(creado.getId())).toUri())
            .body(assembler.toModel(creado));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductosDTO>>> listar() {
        List<EntityModel<ProductosDTO>> productos = service.listar()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(productos,
            linkTo(methodOn(ProductosController.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductosDTO>> obtener(@PathVariable Integer id) {
        ProductosDTO dto = service.obtenerPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(assembler.toModel(dto));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductosDTO>> actualizar(@PathVariable Integer id, @RequestBody ProductosDTO dto) {
        ProductosDTO actualizado = service.actualizar(id, dto);
        if (actualizado != null) {
            return ResponseEntity.ok(assembler.toModel(actualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
