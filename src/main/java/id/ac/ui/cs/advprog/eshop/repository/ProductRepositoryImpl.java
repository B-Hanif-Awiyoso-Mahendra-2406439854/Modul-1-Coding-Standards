package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    @Override
    public Product save(Product product) {
        productData.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productData);
    }

    @Override
    public Product update(Product updatedProduct) {
        for (int i = 0; i < productData.size(); i++) {
            Product product = productData.get(i);
            if (product.getProductId().equals(updatedProduct.getProductId())) {
                productData.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        productData.removeIf(product -> product.getProductId().equals(id));
    }
}
