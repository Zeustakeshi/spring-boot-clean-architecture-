## **QUY TẮC VÀNG (GOLDEN RULES) VỀ CẤU TRÚC DỰ ÁN SPRING BOOT THEO CLEAN ARCHITECTURE**

**Phiên bản:** 1.0
**Ngày ban hành:** 06/08/2025

### **1. Triết lý và Mục tiêu**

Tài liệu này định nghĩa các quy tắc bắt buộc khi xây dựng và phát triển dự án. Mục tiêu của chúng ta không phải là hoàn thành tính năng nhanh nhất có thể, mà là xây dựng một hệ thống **dễ bảo trì, dễ mở rộng, dễ kiểm thử, và dễ dàng cho thành viên mới tham gia**.

Chúng ta tuân thủ nghiêm ngặt **Clean Architecture** để tách biệt nghiệp vụ (business logic) khỏi các chi tiết kỹ thuật (framework, database, UI). Điều này giúp hệ thống linh hoạt và có tuổi thọ cao. **Viết code cho người khác đọc, không chỉ cho máy chạy.**

### **2. Quy tắc Bất di bất dịch: Nguyên tắc Phụ thuộc (The Dependency Rule)**

Đây là quy tắc tối cao. Mọi dòng code đều phải tuân thủ.

> **MỌI PHỤ THUỘC CHỈ ĐƯỢC HƯỚNG VÀO TRONG.**

  * `Infrastructure` **PHẢI** phụ thuộc vào `Application` (Use Cases).
  * `Application` **PHẢI** phụ thuộc vào `Domain`.
  * `Domain` **TUYỆT ĐỐI KHÔNG** phụ thuộc vào bất kỳ tầng nào khác.
  * `Application` **TUYỆT ĐỐI KHÔNG** được biết đến sự tồn tại của `Infrastructure`.

Vi phạm quy tắc này sẽ làm sụp đổ toàn bộ kiến trúc.

### **3. Cấu trúc Module và Package**

Dự án **BẮT BUỘC** được chia thành các module (Maven/Gradle) như sau:

```
my-app/
├── core/                     // Module lõi, chứa toàn bộ business logic
│   ├── core-domain/
│   └── core-application/
│
├── infrastructure/           // Module hạ tầng, chứa các chi tiết kỹ thuật
│
├── bootstrap/                // Module khởi động ứng dụng Spring Boot
│
└── pom.xml (hoặc build.gradle)
```

#### **3.1. Module `core-domain` (Trái tim của nghiệp vụ)**

  * **Mục đích:** Chứa các đối tượng và quy tắc nghiệp vụ cốt lõi nhất, tồn tại độc lập với ứng dụng.
  * **Thành phần:**
      * **Entities:** Các đối tượng nghiệp vụ (ví dụ: `Product`, `Order`).
      * **Value Objects:** Các đối tượng giá trị không có định danh (ví dụ: `Money`, `Address`).
      * **Domain Exceptions:** Các ngoại lệ nghiệp vụ cốt lõi (ví dụ: `InvalidStockQuantityException`).
      * **Domain Services:** Logic nghiệp vụ phức tạp liên quan đến nhiều entities (ít dùng).
  * **RÀNG BUỘC TUYỆT ĐỐI:**
      * **KHÔNG** chứa bất kỳ annotation nào của framework (`@Entity`, `@Component`, `@Data` của Lombok, v.v...). Đây là các POJO (Plain Old Java Object) thuần túy.
      * **KHÔNG** có bất kỳ dependency nào ngoài JDK và các thư viện utility đã được duyệt (ví dụ: Guava, Apache Commons Lang).
      * **KHÔNG** biết gì về database, API, hay bất kỳ công nghệ nào.
      * Các `Entities` phải luôn ở trạng thái hợp lệ (self-validated).

#### **3.2. Module `core-application` (Các quy trình của ứng dụng)**

  * **Mục đích:** Điều phối các `Entities` để thực hiện các kịch bản sử dụng (use cases) của ứng dụng.
  * **Thành phần:**
      * **Use Cases:** Các class thực thi một nghiệp vụ cụ thể (ví dụ: `CreateProductUseCase.java`).
      * **Ports (Cổng):** Định nghĩa các `interface` để giao tiếp với thế giới bên ngoài.
          * **Input Ports:** Giao diện mà tầng `Infrastructure` sẽ gọi vào. Thường là chính các `UseCase` interface.
          * **Output Ports:** Giao diện cho việc truy xuất dữ liệu hoặc gửi sự kiện (ví dụ: `ProductRepositoryPort.java`, `OrderEventPublisherPort.java`).
  * **RÀNG BUỘC TUYỆT ĐỐI:**
      * Chỉ phụ thuộc vào `core-domain`.
      * **KHÔNG** chứa code implement chi tiết cho `Output Ports`.
      * **KHÔNG** chứa bất kỳ dependency nào liên quan đến Spring, JPA, Web, Messaging...

#### **3.3. Module `infrastructure` (Thế giới bên ngoài)**

  * **Mục đích:** Chứa tất cả các chi tiết kỹ thuật, là cầu nối giữa `core` và công nghệ.
  * **Thành phần (Adapters):**
      * **Input Adapters:**
          * `web/`: Chứa các Spring RestController.
          * `messaging/listener/`: Chứa các Kafka/RabbitMQ listener.
      * **Output Adapters:**
          * `persistence/`: Chứa các implementation của `Output Port` sử dụng Spring Data JPA (`@Repository`).
          * `messaging/publisher/`: Chứa các implementation để gửi message.
      * `config/`: Chứa các cấu hình Spring Beans (`@Configuration`).
      * `mapper/`: Chứa các class Mapper để chuyển đổi giữa `Entity`, `JPA Model` và `DTO`.
      * `security/`: Chứa cấu hình Spring Security.
  * **RÀNG BUỘC:**
      * Phụ thuộc vào `core-application` và `core-domain`.
      * **Controller TUYỆT ĐỐI KHÔNG** được chứa business logic. Nhiệm vụ duy nhất của nó là:
        1.  Nhận HTTP request.
        2.  Validate input (sử dụng DTO và validation annotation).
        3.  Gọi Use Case tương ứng.
        4.  Chuyển đổi kết quả trả về của Use Case thành HTTP response.
      * **Repository implementation BẮT BUỘC** phải `implement` một `Output Port` từ `core-application`.

#### **3.4. Module `bootstrap` (Khởi động)**

  * **Mục đích:** Chỉ chứa class `@SpringBootApplication` để khởi chạy ứng dụng.
  * **RÀNG BUỘC:** Module này phụ thuộc vào tất cả các module khác để "kết nối" chúng lại với nhau.

-----

### **4. Quy tắc Đặt tên (Naming Conventions)**

Sự nhất quán trong đặt tên là tối quan trọng để dễ đọc và dễ tìm kiếm.

| Loại | Quy tắc | Ví dụ |
| --- | --- | --- |
| **Package** | `com.company.project.[feature]` hoặc `com.company.project.[layer]` tùy ngữ cảnh | `com.mycompany.ecommerce.product` |
| **Domain Entity** | Danh từ, thể hiện khái niệm nghiệp vụ. | `Product`, `Order`, `Customer` |
| **Use Case** | `[Action][Entity]UseCase` | `CreateProductUseCase`, `GetProductByIdUseCase` |
| **Input Port** | `[Action][Entity]InputPort` (hoặc dùng luôn interface UseCase) | `CreateProductInputPort` |
| **Output Port** | `[Entity][Action]Port` | `ProductRepositoryPort`, `OrderEventPublisherPort` |
| **Controller** | `[Feature]Controller` | `ProductController` |
| **JPA Entity** | `[EntityName]JpaEntity` hoặc `[EntityName]Model` | `ProductJpaEntity`, `OrderJpaModel` |
| **Repository (JPA)** | `[EntityName]JpaRepository` | `ProductJpaRepository` |
| **DTO (Request)** | `[Action][Entity]Request` | `CreateProductRequest`, `UpdateProductRequest` |
| **DTO (Response)** | `[Entity]Response` | `ProductResponse`, `CustomerDetailsResponse` |
| **Mapper** | `[Source]To[Target]Mapper` | `ProductMapper`, `ProductJpaMapper` |

-----

### **5. Các ví dụ về code "KHÔNG SẠCH" và cách sửa (Anti-Patterns)**

#### **Anti-Pattern 1: Controller chứa business logic**

🔴 **SAI:**

```java
// ProductController.java - TRONG INFRASTRUCTURE
@RestController
public class ProductController {
    @Autowired
    private ProductJpaRepository productRepo; // SAI: Controller không được biết đến JPA Repo

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
        if (req.getPrice() <= 0) { // SAI: Business logic nằm trong controller
            throw new IllegalArgumentException("Price must be positive");
        }
        Product p = new Product(req.getName(), req.getPrice());
        return ResponseEntity.ok(productRepo.save(p)); // SAI: Trả về JPA Entity
    }
}
```

🟢 **ĐÚNG:**

```java
// CreateProductUseCase.java - TRONG CORE-APPLICATION
public class CreateProductUseCase {
    private final ProductRepositoryPort productRepoPort;

    public Product execute(CreateProductCommand command) {
        if (command.getPrice() <= 0) {
            throw new InvalidPriceException("Price must be positive");
        }
        Product product = new Product(command.getName(), Money.of(command.getPrice()));
        return productRepoPort.save(product);
    }
}

// ProductController.java - TRONG INFRASTRUCTURE
@RestController
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper productMapper;

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest req) {
        // Chỉ delegate, không có logic
        var command = productMapper.toCommand(req);
        Product newProduct = createProductUseCase.execute(command);
        return ResponseEntity.ok(productMapper.toResponse(newProduct));
    }
}
```

#### **Anti-Pattern 2: Domain Entity chứa annotation của framework**

🔴 **SAI:**

```java
// Product.java - TRONG CORE-DOMAIN
@Entity // SAI! Vi phạm Dependency Rule
@Table(name = "products")
@Data // SAI! Gây ra các vấn đề về mutability và che giấu logic
public class Product {
    @Id // SAI!
    private Long id;
    private String name;
    // ...
}
```

🟢 **ĐÚNG:**

```java
// Product.java - TRONG CORE-DOMAIN
public class Product { // POJO thuần túy
    private final ProductId id;
    private String name;
    private Money price;

    // Constructor và các method đảm bảo entity luôn hợp lệ
    public Product(String name, Money price) {
        // validation logic...
        this.name = name;
        this.price = price;
    }
    // getters...
}

// ProductJpaEntity.java - TRONG INFRASTRUCTURE
@Entity
@Table(name = "products")
public class ProductJpaEntity {
    @Id
    private Long id;
    // ...
}
```

#### **Anti-Pattern 3: Trả về Entity/JPA Model từ API**

  * **Lý do sai:**
      * Lộ cấu trúc database ra bên ngoài, là một lỗ hổng bảo mật.
      * Tạo ra sự phụ thuộc chặt chẽ giữa client và database schema. Thay đổi cột trong DB có thể làm hỏng client.
      * Có thể gây ra lỗi Lazy Initialization Exception.
  * **Giải pháp:** **LUÔN LUÔN** sử dụng **DTO (Data Transfer Object)** cho request và response.

-----

### **6. Quy tắc về Clean Code và SOLID**

  * **S - Single Responsibility Principle:** Mỗi class/method chỉ làm một việc và làm tốt việc đó.
      * `UseCase` chỉ làm một nghiệp vụ.
      * `Controller` chỉ xử lý web.
      * `Mapper` chỉ chuyển đổi dữ liệu.
  * **O - Open/Closed Principle:** Sẵn sàng cho mở rộng, đóng với sửa đổi.
      * Khi có nghiệp vụ mới, ta tạo `UseCase` mới, không sửa `UseCase` cũ.
  * **L - Liskov Substitution Principle:** Lớp con có thể thay thế lớp cha mà không làm thay đổi tính đúng đắn của chương trình. (Quan trọng khi implement các Port).
  * **I - Interface Segregation Principle:** Tạo các `Port` nhỏ, chuyên biệt thay vì một `Port` lớn, chung chung.
      * Nên có `ProductRepositoryPort`, `UserRepositoryPort` thay vì một `DatabasePort` duy nhất.
  * **D - Dependency Inversion Principle:** Chính là **The Dependency Rule** của Clean Architecture.

### **7. Cam kết**

Mọi thành viên trong team cam kết đọc hiểu và tuân thủ các quy tắc trong tài liệu này. Mọi Pull Request vi phạm các quy tắc trên sẽ bị từ chối cho đến khi được sửa lại cho đúng.

Đây là nền tảng để chúng ta xây dựng một sản phẩm đáng tự hào.
