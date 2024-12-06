package com.NAKexample.demoSpring.controllers;

import com.NAKexample.demoSpring.models.Product;
import com.NAKexample.demoSpring.models.ResponseObject;
import com.NAKexample.demoSpring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {

    //DI
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    //http://localhost:8080/api/v1/Products
    List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //return object with: data, message, statuss
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = productRepository.findById(id);
        return  foundProduct.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get product successfully", foundProduct)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Cannot find product with id", "")
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct ){

        List<Product> productFound = productRepository.findByProductName((newProduct.getProductName()));
        if(productFound.size() >0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Product name already exist", "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "insert successfully", productRepository.save((newProduct)))
        );
    }

    @PutMapping("/{id}")
    @Transactional
    ResponseEntity<ResponseObject> updateOrInsertProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product updatedProduct = productRepository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setProductYear(newProduct.getProductYear());
                    product.setPrice(newProduct.getPrice());
                    product.setUrl(newProduct.getUrl());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    //newProduct.setId(id);
                    return productRepository.save(newProduct);
                });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update or insert product successful", updatedProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean isExist = productRepository.existsById(id);
        if(isExist){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successful", "")
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("ok", "Not found product", "")
        );
    }
}
