package com.example.vidaplus.repositories;

import com.example.vidaplus.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
