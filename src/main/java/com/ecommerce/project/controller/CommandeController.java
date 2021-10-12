package com.ecommerce.project.controller;

import com.ecommerce.project.models.Commande;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.CommandeRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CommandeController {

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "commande/create")
    public ResponseEntity<Void> createCommande (@RequestBody Commande commande) {

        Commande c = new Commande();
        c.setUser(userRepository.findByEmail(commande.getUser().getEmail()));
        c.setDate(commande.getDate());

        final List<Product> collect = commande.getProducts().stream().map(product -> (productRepository.findById(product.getId()))).collect(Collectors.toList());
        c.setProducts(collect);

        Commande E = commandeRepository.save(c);

        if (E != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(E.getId()).toUri();
            return ResponseEntity.created(location).build();

        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("commande/all")
    public List<Commande> getAllCommande() {
        List<Commande> commandes = (List<Commande>) commandeRepository.findAll();
        return commandes ;
    }
}
