package com.noumenmdg.microcommerce.web.dao;

import com.noumenmdg.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findAll();
    Product findById(int id);
    Product save(Product product);

    List<Product> findByPriceGreaterThan(int minPrice);

    @Query("SELECT p FROM Product p WHERE p.price > :minPrice ")
    List<Product> findExpensiveProducts(@Param("minPrice") int price);
}
