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


---

## Reflection 2

### Refleksi tentang Unit Testing

#### 1. Perasaan Setelah Menulis Unit Test

Setelah menulis unit test untuk fitur Edit dan Delete Product, saya merasakan beberapa hal berikut:

**Positif:**
- **Kepercayaan diri meningkat** - Unit test memberikan confidence bahwa kode yang ditulis berfungsi sesuai ekspektasi, baik untuk skenario normal maupun edge cases.
- **Dokumentasi hidup** - Test cases berfungsi sebagai dokumentasi yang menjelaskan bagaimana setiap method seharusnya bekerja.
- **Kemudahan refactoring** - Dengan adanya test suite yang comprehensive, saya lebih berani melakukan refactoring karena test akan langsung mendeteksi jika ada breaking changes.
- **Deteksi bug lebih awal** - Beberapa edge cases yang mungkin terlewat saat coding, teridentifikasi saat menulis test (contoh: delete product yang tidak ada, update dengan null values).

**Tantangan:**
- **Time-consuming** - Menulis test membutuhkan waktu tambahan, terutama untuk setup mocking dan assertion yang detail.
- **Maintenance overhead** - Test juga perlu di-maintain ketika ada perubahan requirement atau refactoring.
- **Kompleksitas setup** - Penggunaan Mockito dan understanding tentang mocking membutuhkan learning curve tersendiri.

#### 2. Berapa Banyak Unit Test yang Seharusnya Dibuat?

Tidak ada angka pasti berapa banyak unit test yang harus dibuat dalam sebuah class, namun ada beberapa prinsip yang dapat diikuti:

**Prinsip Umum:**
- **Minimal satu test per method** - Setiap public method setidaknya harus memiliki satu test untuk happy path scenario.
- **Test untuk setiap branch logic** - Jika ada if-else, switch-case, atau loop, masing-masing branch harus di-test.
- **Test untuk boundary conditions** - Test untuk nilai minimum, maksimum, null, empty, zero, dll.
- **Test untuk exception handling** - Jika method bisa throw exception, harus ada test yang verify behavior tersebut.

**Contoh dari project ini:**
Untuk `ProductRepository.update()`, saya membuat 5 test cases:
1. `testUpdateProductSuccess` - Happy path
2. `testUpdateProductNameOnly` - Partial update scenario
3. `testUpdateProductQuantityOnly` - Partial update scenario lainnya
4. `testUpdateProductNotFound` - Negative scenario (product tidak ada)
5. `testUpdateProductInEmptyRepository` - Edge case (repository kosong)

**Rule of Thumb:**
- Simple getter/setter biasanya tidak perlu unit test
- Business logic harus comprehensive (mencakup positive & negative scenarios)
- Rata-rata 3-7 test cases per method adalah reasonable, tergantung kompleksitas

#### 3. Memastikan Unit Test Sudah Cukup: Code Coverage

**Apa itu Code Coverage?**

Code coverage adalah metrik yang mengukur seberapa banyak source code yang tereksekusi ketika test suite dijalankan. Code coverage biasanya diukur dalam persentase dan terdiri dari beberapa jenis:

1. **Line Coverage** - Persentase baris kode yang tereksekusi
2. **Branch Coverage** - Persentase cabang logika (if/else) yang tereksekusi
3. **Method Coverage** - Persentase method yang dipanggil
4. **Class Coverage** - Persentase class yang di-test

#### 4. Apakah 100% Code Coverage = Bebas Bug?
Jawaban: Tidak, 100% code coverage BUKAN jaminan bahwa code bebas dari bug atau error. Berikut alasannya:

1. Coverage tidak mengukur kualitas test
2. Tidak mendeteksi logic errors
3. Tidak mencakup integration issues
◦ Unit test mocking dependencies, jadi tidak detect masalah saat integration
◦ Contoh: Database connection issues, network timeouts, dll
4. Tidak mencakup concurrency bugs
◦ Race conditions, deadlocks tidak terdeteksi oleh unit test biasa
5. Tidak mencakup requirement gaps
◦ Code bisa 100% ter-cover tapi tidak implement requirement yang benar
Bug yang tidak terdeteksi:
1. Apa yang terjadi jika updatedProduct adalah null? → NullPointerException
2. Apa yang terjadi jika productId adalah null? → NullPointerException
3. Apa yang terjadi dengan concurrent modification? → ConcurrentModificationException

Kesimpulan:
Code coverage adalah tool yang berguna untuk:
1. Mengidentifikasi area yang belum di-test
2. Tracking progress testing
3. Memastikan tidak ada dead code

Namun, code coverage bukan satu-satunya metrik. Yang lebih penting adalah:
1. Quality of tests - Apakah assertion meaningful?
2. Edge cases coverage - Apakah boundary conditions ter-test?
3. Business logic verification - Apakah requirement terpenuhi?
4. Integration testing - Apakah components bekerja together?
5. Manual testing - User perspective masih penting

Best Practice:

1. Menggunakan code coverage sebagai guideline, bukan goal
2. Focus on meaningful tests bukan hanya achieving high percentage
3. Kombinasikan dengan integration test, functional test, dan manual testing
4. Review test quality, bukan hanya coverage numbers

---
### Refleksi tentang Code Cleanliness pada Functional Test Suite

Setelah menulis `CreateProductFunctionalTest.java` dan kemudian diminta membuat functional test suite baru untuk memverifikasi jumlah item dalam product list, saya mengidentifikasi beberapa isu clean code yang signifikan.
Jika saya membuat class baru dengan setup procedures dan instance variables yang identik (seperti `@SpringBootTest`, `@ExtendWith(SeleniumJupiter.class)`, `serverPort`, `testBaseUrl`, dan method `setUpTest()`), ini jelas merupakan **pelanggaran prinsip DRY (Don't Repeat Yourself)**.
Code duplication seperti ini akan menurunkan kualitas kode karena menciptakan maintenance burden—setiap perubahan konfigurasi (misalnya mengubah base URL atau menambahkan authentication) harus direplikasi di semua test classes. 
Selain itu, duplikasi ini meningkatkan risiko inconsistency dan memperbesar codebase tanpa menambah value yang berarti.

Masalah utama yang teridentifikasi adalah **shotgun surgery code smell**, di mana satu perubahan requirement memerlukan modifikasi di banyak tempat. 
Hal ini juga melanggar **Single Responsibility Principle** karena test class seharusnya fokus pada logic testing, bukan infrastructure setup. 
Untuk mengatasi masalah ini, saya dapat menerapkan **Base Test Class** dengan inheritance—membuat abstract class `BaseFunctionalTest` yang berisi shared setup, instance variables, dan helper methods yang dapat di-reuse oleh semua functional test classes.
Pendekatan ini akan mengeliminasi duplikasi dan memastikan consistency across all tests.

Sebagai improvement lebih lanjut, saya juga dapat mengimplementasikan **Page Object Model (POM) pattern**. 
Dengan membuat class seperti `ProductListPage` dan `CreateProductPage` yang meng-encapsulate UI interactions dan locators, test code akan menjadi lebih readable dan maintainable. 
POM memisahkan concerns antara "bagaimana melakukan action" (di Page Object) dengan "apa yang di-test" (di test class), sehingga jika ada perubahan UI, kita hanya perlu update Page Object tanpa menyentuh test logic. 
Kombinasi Base Test Class dan Page Object Model akan meningkatkan code quality secara signifikan, mengurangi duplication dari ~40% menjadi ~5%, dan membuat test suite lebih scalable untuk pengembangan fitur selanjutnya.

**Nama:** Hanif Awiyoso Mahendra
**NPM:** 2406439854
**Kelas:** Advanced Programming - B
**Tanggal:** 8 Februari 2026