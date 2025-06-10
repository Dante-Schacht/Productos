package com.Productos.services;

import com.Productos.models.Producto;
import com.Productos.dto.ProductosDTO;
import com.Productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductosService {

    @Autowired
    private ProductoRepository productoRepository;

    private ProductosDTO toDTO(Producto producto) {
        return new ProductosDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getCategoria(),
                producto.getActivo()
        );
    }

    private Producto toEntity(ProductosDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId()); // importante para actualizar
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(dto.getActivo());
        return producto;
    }

    public ProductosDTO crear(ProductosDTO dto) {
        Producto producto = toEntity(dto);
        return toDTO(productoRepository.save(producto));
    }

    public List<ProductosDTO> listar() {
        return productoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductosDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toDTO(producto);
    }

    public ProductosDTO actualizar(Integer id, ProductosDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecioUnitario(dto.getPrecioUnitario());
        existente.setCategoria(dto.getCategoria());
        existente.setActivo(dto.getActivo());

        return toDTO(productoRepository.save(existente));
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }
}