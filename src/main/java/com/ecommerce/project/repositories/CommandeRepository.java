package com.ecommerce.project.repositories;

import com.ecommerce.project.models.Commande;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends CrudRepository<Commande,Integer> {
}
