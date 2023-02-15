package com.noumenmdg.microcommerce.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.noumenmdg.microcommerce.model.Product;
import com.noumenmdg.microcommerce.web.dao.ProductDao;
import com.noumenmdg.microcommerce.web.exceptions.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Api("API for products CRUD operations")
@RestController
public class ProductController {
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @GetMapping("/products")
    public MappingJacksonValue listProducts(){
        List<Product> products = productDao.findAll();
        SimpleBeanPropertyFilter myFilter =
                SimpleBeanPropertyFilter.serializeAllExcept("purchasePrice");
        FilterProvider filterList = new
                SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);
        MappingJacksonValue filteredProducts = new MappingJacksonValue(products);
        filteredProducts.setFilters(filterList);
        return filteredProducts;
    }

    @ApiOperation("Show a product by its Id provided that it is stored in the DB")
    @GetMapping("/products/{id}")
    public Product showProduct(@PathVariable int id){
        Product product = productDao.findById(id);
        if(product == null) throw new ProductNotFoundException("The product with id "+ id+" id not found");
        return product;
    }

    @GetMapping(value = "test/products/{minPrice}")
    public List<Product> requestTest(@PathVariable int minPrice)
    {
        //return productDao.findByPriceGreaterThan(minPrice);
        return productDao.findExpensiveProducts(minPrice);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
        Product productAdded = productDao.save(product);
        if(Objects.isNull(productAdded)){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/products/{id}")
    public void removeProduct(@PathVariable int id){
        productDao.deleteById(id);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product product){
        productDao.save(product);
    }
}
