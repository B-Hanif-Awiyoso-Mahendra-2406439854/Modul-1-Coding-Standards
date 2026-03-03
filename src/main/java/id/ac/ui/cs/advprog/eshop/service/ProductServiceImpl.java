package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, IdGenerator idGenerator) {
        this.productRepository = productRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Product create(Product product){
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(idGenerator.generateId());
        }
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll(){
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String id){
        return productRepository.findById(id);
    }

    @Override
    public Product update(Product updatedProduct){
        return productRepository.update(updatedProduct);
    }

    @Override
    public void delete(String id){
        productRepository.delete(id);
    }
}
