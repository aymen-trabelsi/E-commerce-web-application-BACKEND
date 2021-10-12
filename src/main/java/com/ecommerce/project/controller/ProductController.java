package com.ecommerce.project.controller;

import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")

public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "product/create")
    public ResponseEntity<Void> createProduct (@RequestBody Product product) {


        Product p = new Product();

        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setImage("../../../assets/home/img/gallery/"+product.getImage());
        p.setEtat(0);



        Product E = productRepository.save(p);
        if (E != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(E.getId()).toUri();
            return ResponseEntity.created(location).build();

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("product/all")
    public List<Product> getAllProduct() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products ;
    }

    @GetMapping("product/{id}")
    public Product getAllProduct(@PathVariable int id) {
        Product product = productRepository.findById(id);
        return product ;
    }

    @GetMapping("product/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        Product p = productRepository.findById(id);
        if(p != null ){
            p.setEtat(1);
            productRepository.save(p);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }
}
