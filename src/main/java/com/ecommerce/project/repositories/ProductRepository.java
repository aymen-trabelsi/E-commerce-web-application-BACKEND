package com.ecommerce.project.repositories;
import com.ecommerce.project.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
    Product findById(int id);
}
