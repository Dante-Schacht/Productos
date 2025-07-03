package com.Productos.assembler;

import com.Productos.controllers.ProductosController;
import com.Productos.dto.ProductosDTO;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductosAssembler implements RepresentationModelAssembler<ProductosDTO, EntityModel<ProductosDTO>> {

    @Override
    public EntityModel<ProductosDTO> toModel(ProductosDTO producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductosController.class).obtener(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductosController.class).listar()).withRel("productos")
        );
    }
}
