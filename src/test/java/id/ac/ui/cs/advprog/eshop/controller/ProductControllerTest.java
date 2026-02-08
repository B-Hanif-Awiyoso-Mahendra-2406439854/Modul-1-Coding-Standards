package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateProductPage() {
        String result = productController.createProductPage(model);

        assertEquals("CreateProduct", result);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productService.create(product)).thenReturn(product);

        String result = productController.createProductPost(product, model);

        assertEquals("redirect:list", result);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        products.add(product1);

        when(productService.findAll()).thenReturn(products);

        String result = productController.productListPage(model);

        assertEquals("ProductList", result);
        verify(productService, times(1)).findAll();
        verify(model, times(1)).addAttribute("products", products);
    }

    @Test
    void testEditProductPageSuccess() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productService.findById(productId)).thenReturn(product);

        String result = productController.editProductPage(productId, model);

        assertEquals("EditProduct", result);
        verify(productService, times(1)).findById(productId);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPageWithDifferentProduct() {
        String productId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Usep");
        product.setProductQuantity(50);

        when(productService.findById(productId)).thenReturn(product);

        String result = productController.editProductPage(productId, model);

        assertEquals("EditProduct", result);
        verify(productService, times(1)).findById(productId);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPageNotFound() {
        String productId = "non-existent-id";

        when(productService.findById(productId)).thenReturn(null);

        String result = productController.editProductPage(productId, model);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).findById(productId);
        verify(model, never()).addAttribute(eq("product"), any());
    }

    @Test
    void testEditProductPageWithNullId() {
        when(productService.findById(null)).thenReturn(null);

        String result = productController.editProductPage(null, model);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).findById(null);
        verify(model, never()).addAttribute(eq("product"), any());
    }

    @Test
    void testEditProductPageWithEmptyId() {
        String emptyId = "";

        when(productService.findById(emptyId)).thenReturn(null);

        String result = productController.editProductPage(emptyId, model);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).findById(emptyId);
        verify(model, never()).addAttribute(eq("product"), any());
    }

    @Test
    void testUpdateProductSuccess() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang Updated");
        product.setProductQuantity(150);

        when(productService.update(product)).thenReturn(product);

        String result = productController.updateProduct(product);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).update(product);
    }

    @Test
    void testUpdateProductNameOnly() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("New Name");
        product.setProductQuantity(100);

        when(productService.update(product)).thenReturn(product);

        String result = productController.updateProduct(product);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).update(product);
    }

    @Test
    void testUpdateProductQuantityOnly() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(200);

        when(productService.update(product)).thenReturn(product);

        String result = productController.updateProduct(product);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).update(product);
    }

    @Test
    void testUpdateProductWithZeroQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(0);

        when(productService.update(product)).thenReturn(product);

        String result = productController.updateProduct(product);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).update(product);
    }

    @Test
    void testUpdateProductNotFound() {
        Product product = new Product();
        product.setProductId("non-existent-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productService.update(product)).thenReturn(null);

        String result = productController.updateProduct(product);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).update(product);
    }

    @Test
    void testDeleteProductSuccess() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        doNothing().when(productService).delete(productId);

        String result = productController.deleteProduct(productId);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete(productId);
    }

    @Test
    void testDeleteDifferentProduct() {
        String productId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        doNothing().when(productService).delete(productId);

        String result = productController.deleteProduct(productId);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete(productId);
    }

    @Test
    void testDeleteMultipleProducts() {
        String productId1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String productId2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        doNothing().when(productService).delete(productId1);
        doNothing().when(productService).delete(productId2);

        String result1 = productController.deleteProduct(productId1);
        String result2 = productController.deleteProduct(productId2);

        assertEquals("redirect:/product/list", result1);
        assertEquals("redirect:/product/list", result2);
        verify(productService, times(1)).delete(productId1);
        verify(productService, times(1)).delete(productId2);
    }

    @Test
    void testDeleteProductNotFound() {
        String nonExistentId = "non-existent-id";

        doNothing().when(productService).delete(nonExistentId);

        String result = productController.deleteProduct(nonExistentId);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete(nonExistentId);
    }

    @Test
    void testDeleteProductWithNullId() {
        doNothing().when(productService).delete(null);

        String result = productController.deleteProduct(null);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete(null);
    }

    @Test
    void testDeleteProductWithEmptyId() {
        String emptyId = "";

        doNothing().when(productService).delete(emptyId);

        String result = productController.deleteProduct(emptyId);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete(emptyId);
    }
}