package com.Productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.productos"})
public class ProductosApiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApiSpringBootApplication.class, args);
	}
	

}
