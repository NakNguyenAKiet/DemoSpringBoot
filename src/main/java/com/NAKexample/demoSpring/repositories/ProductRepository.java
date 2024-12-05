package com.NAKexample.demoSpring.repositories;

import com.NAKexample.demoSpring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
