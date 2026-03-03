package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, String> {
    
    Product save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();
    Product update(Product product);
    void delete(String id);

}
