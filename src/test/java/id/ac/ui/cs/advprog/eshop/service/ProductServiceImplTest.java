package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertNotNull(result.getProductId());
        assertEquals("Sampo Cap Bambang", result.getProductName());
        assertEquals(100, result.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals(product1.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

        Product result = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(result);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", result.getProductId());
        assertEquals("Sampo Cap Bambang", result.getProductName());
        verify(productRepository, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById("non-existent-id")).thenReturn(null);

        Product result = productService.findById("non-existent-id");

        assertNull(result);
        verify(productRepository, times(1)).findById("non-existent-id");
    }

    @Test
    void testUpdateProductSuccess() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(150);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", result.getProductId());
        assertEquals("Sampo Cap Bambang Updated", result.getProductName());
        assertEquals(150, result.getProductQuantity());
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductNameOnly() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("New Product Name");
        updatedProduct.setProductQuantity(100);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals("New Product Name", result.getProductName());
        assertEquals(100, result.getProductQuantity());
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductQuantityOnly() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(200);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Bambang", result.getProductName());
        assertEquals(200, result.getProductQuantity());
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductWithZeroQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(0);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals(0, result.getProductQuantity());
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(100);

        when(productRepository.update(updatedProduct)).thenReturn(null);

        Product result = productService.update(updatedProduct);

        assertNull(result);
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateNullProduct() {
        when(productRepository.update(null)).thenReturn(null);

        Product result = productService.update(null);

        assertNull(result);
        verify(productRepository, times(1)).update(null);
    }

    @Test
    void testDeleteProductSuccess() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        doNothing().when(productRepository).delete(productId);

        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testDeleteMultipleProducts() {
        String productId1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String productId2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        doNothing().when(productRepository).delete(productId1);
        doNothing().when(productRepository).delete(productId2);

        productService.delete(productId1);
        productService.delete(productId2);

        verify(productRepository, times(1)).delete(productId1);
        verify(productRepository, times(1)).delete(productId2);
    }

    // DELETE PRODUCT TESTS - Negative Scenarios
    @Test
    void testDeleteProductNotFound() {
        String nonExistentId = "non-existent-id";

        doNothing().when(productRepository).delete(nonExistentId);

        productService.delete(nonExistentId);

        verify(productRepository, times(1)).delete(nonExistentId);
    }

    @Test
    void testDeleteProductWithNullId() {
        doNothing().when(productRepository).delete(null);

        productService.delete(null);

        verify(productRepository, times(1)).delete(null);
    }

    @Test
    void testDeleteProductWithEmptyId() {
        String emptyId = "";

        doNothing().when(productRepository).delete(emptyId);

        productService.delete(emptyId);

        verify(productRepository, times(1)).delete(emptyId);
    }
}