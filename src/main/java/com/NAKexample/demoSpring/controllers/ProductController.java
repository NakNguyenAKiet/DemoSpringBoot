package com.NAKexample.demoSpring.controllers;

import com.NAKexample.demoSpring.models.Product;
import com.NAKexample.demoSpring.models.ResponseObject;
import com.NAKexample.demoSpring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {

    //DI
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    //http://localhost:8080/api/v1/Products
    List<Product> getAllProducts(){
        return repository.findAll();
    }

    //return object with: data, message, statuss
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);

        if(foundProduct.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get product successfully", foundProduct)
            );
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Cannot find product with id", "")
            );
        }
    }
}
