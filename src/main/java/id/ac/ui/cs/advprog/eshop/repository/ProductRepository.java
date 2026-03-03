package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface ProductRepository extends Repository<Product, String> {
    
    Product create(Product product);
    Product findById(String id);
    Iterator<Product> findAll();
    Product update(Product product);
    void delete(String id);

}
