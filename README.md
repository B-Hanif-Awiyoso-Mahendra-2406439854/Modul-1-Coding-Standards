# Modul-1-Coding-Standards

## Reflection 1

Setelah mengimplementasikan fitur Edit dan Delete Product menggunakan Spring Boot, saya melakukan evaluasi terhadap kode yang telah dibuat. Berikut analisis clean code principles dan secure coding practices yang sudah diterapkan.

### Clean Code Principles yang Sudah Diterapkan

#### 1. **Penamaan variabel yang tidak random**
- Penamaan class, method, dan variable sudah deskriptif dan jelas
- Contoh: `ProductRepository`, `findById()`, `deleteProduct()` langsung menjelaskan fungsinya
- Konsistensi penamaan: `productData`, `allProducts`, `updatedProduct`

#### 2. **Single Responsibility Principle (SRP)**
- Setiap class memiliki tanggung jawab yang spesifik:
  - `Product` - Model/entity data produk
  - `ProductRepository` - Data access layer
  - `ProductService` - Business logic layer  
  - `ProductController` - Presentation layer
- Pemisahan ini memudahkan maintenance dan testing

#### 3. **DRY (Don't Repeat Yourself)**
- Menggunakan interface `ProductService` untuk modularitas
- Method reusable untuk operasi CRUD
- Menghindari duplikasi kode

#### 4. **Penggunaan anotasi yang jelas**
- `@Repository`, `@Service`, `@Controller` - Spring stereotype annotations
- `@GetMapping`, `@PostMapping` - Request mapping yang jelas
- `@Autowired` - Dependency injection

### Secure Coding Practices yang Sudah Diterapkan

#### 1. **Input Validation**
- Null check di method `editProductPage()` 
- Redirect otomatis jika product tidak ditemukan

#### 2. **UUID untuk Product ID**
- Menggunakan `UUID.randomUUID()` untuk generate ID yang unpredictable
- Lebih aman dibanding sequential ID yang mudah ditebak

#### 3. **Separation of Concerns**
- Pemisahan layer (Repository, Service, Controller) mengurangi risiko security vulnerability

### Kesalahan yang Ditemukan dan Perbaikannya

#### 1. **Missing `@Override` Annotation di ProductServiceImpl**
**Lokasi:** `ProductServiceImpl.java` line 43

**Masalah:**
```java
public void delete(String id){
    productRepository.delete(id);
}
```

**Perbaikan:**
```java
@Override
public void delete(String id){
    productRepository.delete(id);
}
```
**Alasan:** Annotation `@Override` membantu compiler mendeteksi error jika method signature tidak sesuai dengan interface.

#### 2. **Typo CSS Class di ProductList.html**
**Lokasi:** `ProductList.html` line 29

**Masalah:**
```html
<a th:href="@{/product/delete/{id}(id=${product.productId})}"
   class="btn btn-dangeer btn-sm"
```

**Perbaikan:**
```html
<a th:href="@{/product/delete/{id}(id=${product.productId})}"
   class="btn btn-danger btn-sm"
```
**Alasan:** Typo `btn-dangeer` seharusnya `btn-danger`, menyebabkan styling button delete tidak merah.

#### 3. **Malformed Bootstrap CDN Integrity Hash**
**Lokasi:** `CreateProduct.html` line 6

**Masalah:**
```html
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"rel="stylesheet" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERd`knLPMO"
```

**Perbaikan:**
```html
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
```
**Alasan:** Ada backtick (`) di integrity hash dan missing space sebelum `rel`, bisa menyebabkan CDN verification gagal.

#### 4. **Missing Closing Bracket di Thymeleaf Expression**
**Lokasi:** `ProductList.html` line 28 (sudah diperbaiki)

**Masalah:**
```html
<a th:href="@{/product/delete/{id}(id=${product.productId})"
```

**Perbaikan:**
```html
<a th:href="@{/product/delete/{id}(id=${product.productId})}"
```
**Alasan:** Missing `}` menyebabkan template parsing error (Error 500).

### Evaluasi Clean Code & Secure Coding

**Kelebihan:**
- Arsitektur aplikasi mengikuti layered architecture (MVC pattern)
- Code readable dan maintainable
- Penggunaan dependency injection dengan baik
- UUID implementation untuk keamanan ID

**Yang Perlu Ditingkatkan:**
1. **Input validation** - Belum ada validasi untuk `productName` dan `productQuantity`
2. **Error handling** - Perlu try-catch di controller untuk handle exception
3. **Delete method security** - Menggunakan GET request (rentan CSRF), seharusnya POST/DELETE
4. **Logging** - Belum ada logging untuk debugging dan audit trail
5. **Thread safety** - `ArrayList` tidak thread-safe untuk concurrent access

### Kesimpulan

Secara keseluruhan, implementasi fitur Edit dan Delete Product sudah menerapkan clean code principles dengan baik, terutama dalam hal struktur kode dan penamaan. Namun masih ditemukan beberapa kesalahan teknis seperti typo, missing annotation, dan malformed HTML yang perlu diperbaiki. Untuk secure coding practices, masih perlu improvement terutama dalam input validation, error handling, dan penggunaan HTTP method yang tepat untuk operasi delete.

**Nama:** Hanif Awiyoso Mahendra
**NPM:** 2406439854
**Kelas:** Advanced Programming - B  
**Tanggal:** 7 Februari 2026
