package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d/product", testBaseUrl, serverPort);
    }

    @Test
    void createProductPage_hasCorrectTitle(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        // Verify page title
        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createProductPage_hasCorrectHeading(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        // Verify page heading
        String heading = driver.findElement(By.tagName("h3")).getText();
        assertEquals("Create New Product", heading);
    }

    @Test
    void createProductPage_hasRequiredFormElements(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        // Verify Name input field exists
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        assertNotNull(nameInput);
        assertEquals("text", nameInput.getAttribute("type"));

        // Verify Quantity input field exists
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        assertNotNull(quantityInput);
        assertEquals("text", quantityInput.getAttribute("type"));

        // Verify Submit button exists
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertNotNull(submitButton);
        assertEquals("Submit", submitButton.getText());
    }

    @Test
    void createProduct_successfully_redirectsToProductList(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        // Fill in the form
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Sampo Cap Bambang");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("100");

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for redirection
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        // Verify we're on the product list page
        assertTrue(driver.getCurrentUrl().contains("/product/list"));
    }

    @Test
    void createProduct_successfully_productAppearsInList(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        // Fill in the form with unique product name
        String productName = "Sampo Cap Bambang Test " + System.currentTimeMillis();
        String productQuantity = "100";

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        // Verify product appears in the table
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean productFound = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String name = cells.get(0).getText();
                String quantity = cells.get(1).getText();

                if (name.equals(productName) && quantity.equals(productQuantity)) {
                    productFound = true;
                    break;
                }
            }
        }

        assertTrue(productFound, "Created product should appear in the product list");
    }

    @Test
    void createProduct_withDifferentValues_productAppearsCorrectly(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        // Fill in the form with different values
        String productName = "Sabun Cuci Piring " + System.currentTimeMillis();
        String productQuantity = "50";

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        // Verify product appears with correct values
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean productFound = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String name = cells.get(0).getText();
                String quantity = cells.get(1).getText();

                if (name.equals(productName) && quantity.equals(productQuantity)) {
                    productFound = true;
                    break;
                }
            }
        }

        assertTrue(productFound, "Product with correct name and quantity should appear in the list");
    }

    @Test
    void createMultipleProducts_allProductsAppearInList(ChromeDriver driver) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String product1Name = "Product A " + timestamp;
        String product1Quantity = "10";
        String product2Name = "Product B " + timestamp;
        String product2Quantity = "20";

        // Create first product
        driver.get(baseUrl + "/create");
        driver.findElement(By.id("nameInput")).sendKeys(product1Name);
        driver.findElement(By.id("quantityInput")).sendKeys(product1Quantity);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for redirection
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        // Create second product
        driver.get(baseUrl + "/create");
        driver.findElement(By.id("nameInput")).sendKeys(product2Name);
        driver.findElement(By.id("quantityInput")).sendKeys(product2Quantity);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for redirection
        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        // Verify both products appear in the list
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean product1Found = false;
        boolean product2Found = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String name = cells.get(0).getText();
                String quantity = cells.get(1).getText();

                if (name.equals(product1Name) && quantity.equals(product1Quantity)) {
                    product1Found = true;
                }
                if (name.equals(product2Name) && quantity.equals(product2Quantity)) {
                    product2Found = true;
                }
            }
        }

        assertTrue(product1Found, "First product should appear in the list");
        assertTrue(product2Found, "Second product should appear in the list");
    }

    @Test
    void createProduct_withZeroQuantity_productAppearsInList(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        String productName = "Product Zero Qty " + System.currentTimeMillis();
        String productQuantity = "0";

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        // Verify product appears in the table
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean productFound = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String name = cells.get(0).getText();
                String quantity = cells.get(1).getText();

                if (name.equals(productName) && quantity.equals(productQuantity)) {
                    productFound = true;
                    break;
                }
            }
        }

        assertTrue(productFound, "Product with zero quantity should appear in the list");
    }

    @Test
    void createProduct_withLargeQuantity_productAppearsCorrectly(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        String productName = "Product Large Qty " + System.currentTimeMillis();
        String productQuantity = "999999";

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        // Verify product appears with large quantity
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean productFound = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String name = cells.get(0).getText();
                String quantity = cells.get(1).getText();

                if (name.equals(productName) && quantity.equals(productQuantity)) {
                    productFound = true;
                    break;
                }
            }
        }

        assertTrue(productFound, "Product with large quantity should appear correctly in the list");
    }

    @Test
    void productListPage_hasCreateProductButton(ChromeDriver driver) throws Exception {
        // Navigate to product list page
        driver.get(baseUrl + "/list");

        // Verify "Create Product" button exists
        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        assertNotNull(createButton);
        assertTrue(createButton.getAttribute("href").contains("/product/create"));
    }

    @Test
    void productListPage_createButtonNavigatesToCreatePage(ChromeDriver driver) throws Exception {
        // Navigate to product list page
        driver.get(baseUrl + "/list");

        // Click "Create Product" button
        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        createButton.click();

        // Wait for navigation
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/create"));

        // Verify we're on the create product page
        assertTrue(driver.getCurrentUrl().contains("/product/create"));
        assertEquals("Create New Product", driver.getTitle());
    }

    @Test
    void createProduct_withSpecialCharacters_productAppearsCorrectly(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        String productName = "Product @#$%& " + System.currentTimeMillis();
        String productQuantity = "25";

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        // Verify product appears with special characters
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean productFound = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String name = cells.get(0).getText();
                String quantity = cells.get(1).getText();

                if (name.equals(productName) && quantity.equals(productQuantity)) {
                    productFound = true;
                    break;
                }
            }
        }

        assertTrue(productFound, "Product with special characters should appear correctly");
    }

    @Test
    void createProduct_verifyProductHasEditAndDeleteButtons(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/create");

        String productName = "Product Actions Test " + System.currentTimeMillis();
        String productQuantity = "75";

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        // Find the product row and verify action buttons exist
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean foundWithActions = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                String name = cells.get(0).getText();

                if (name.equals(productName)) {
                    // Check for Edit button
                    WebElement editButton = cells.get(2).findElement(By.linkText("Edit"));
                    assertNotNull(editButton);
                    assertTrue(editButton.getAttribute("href").contains("/product/edit/"));

                    // Check for Delete button
                    WebElement deleteButton = cells.get(2).findElement(By.linkText("Delete"));
                    assertNotNull(deleteButton);
                    assertTrue(deleteButton.getAttribute("href").contains("/product/delete/"));

                    foundWithActions = true;
                    break;
                }
            }
        }

        assertTrue(foundWithActions, "Product should have Edit and Delete action buttons");
    }
}