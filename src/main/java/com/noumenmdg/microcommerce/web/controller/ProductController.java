package com.noumenmdg.microcommerce.web.controller;

import com.noumenmdg.microcommerce.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public String listProducts(){
        return "List of products";
    }

    @GetMapping("/products/{id}")
    public Product showProduct(@PathVariable int id){
        Product product = new Product(id, new String("Gourde"), 100 );
        return product;
    }
}
