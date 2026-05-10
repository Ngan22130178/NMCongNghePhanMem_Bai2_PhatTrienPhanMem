# 🎮 Dự án: My Silly Bestie

Hệ thống mô phỏng tương tác thú cưng ảo tập trung vào phản hồi cảm giác chân thực, xây dựng trên nền tảng Spring Boot và Java 21.

---

## a. Giới thiệu tổng quan về đề tài (Business Requirement)

### 1. Tầm nhìn dự án (Project Vision)
**My Silly Bestie** không đơn thuần là một trò chơi, mà là một không gian mô phỏng sự tương tác giữa chủ và những "người bạn" thú đặc biệt tại gia. Dự án tập trung vào trải nghiệm cảm xúc, nơi người chơi học cách chăm sóc và chung sống với những tính cách tinh nghịch. 

Trò chơi đặt ra một góc nhìn đảo ngược: Người chơi đôi khi sẽ nhận ra chính mình mới là "người bạn hai chân ngớ ngẩn" đang bị xoay quanh bởi những thực thể bốn chân thông minh và lém lỉnh.

### 2. Mục tiêu nghiệp vụ (Business Objectives)
* **Xây dựng sự gắn kết:** Người chơi cảm nhận được sự thân thiết thông qua các hành động vuốt ve, chải lông với **hiệu ứng kích thích cảm giác chân thực**.
* **Cơ chế tương tác vui nhộn:** Hiện thực hóa logic "Trêu đùa có chừng mực" (Prank) và các phản ứng đáp trả lém lỉnh từ thú cưng.
* **Giao diện thân thiện:** Sử dụng màu sắc ấm áp, bối cảnh phòng khách/sân vườn tại gia nhằm tạo sự gần gũi.

### 3. Quy trình tương tác (Business Process)

#### 3.1. Trạng thái nghỉ (Idle)
Thú cưng thực hiện các hành động tự do (ngủ, ngáp, nhìn quanh). Người chơi bắt đầu chọn dụng cụ (chăm sóc hoặc trêu đùa) để bắt đầu phiên tương tác.

#### 3.2. Chế độ Chăm sóc (Care Mode)
Người chơi thực hiện đúng hành động yêu cầu theo chỉ dẫn của hệ thống (ví dụ: chải lông, sấy khô).
* **Kết quả:** Tăng chỉ số Hạnh phúc (Happiness), kích hoạt **âm thanh thư giãn sống động**.

#### 3.3. Chế độ Trêu đùa (Silly Prank Mode)
Người chơi thực hiện các hành động nghịch ngợm. 
* **Cảnh báo:** Thanh Cảnh giác (Suspicion) sẽ rung lên để báo hiệu giới hạn chịu đựng của thú cưng.
* **Kết quả:** Nếu vượt ngưỡng, thú cưng sẽ thực hiện một "Phản ứng đáp trả" (Penalty) mang tính hài hước, dẫn đến kết thúc lượt chơi.

---

## b. Lược đồ Use Case của nhóm

Hệ thống phân tách rõ ràng giữa hành động của người dùng và các xử lý logic tự động từ phía máy chủ.

![Sơ đồ Use Case](/doc/LuocDoUsecase.drawio.svg)
*Hình 3.1: Sơ đồ Use Case tổng quát của hệ thống My Silly Bestie*

---

## c. Kiến trúc hệ thống (System Architecture)
Dự án áp dụng mô hình kiến trúc phân lớp (Layered Architecture) để đảm bảo tính dễ bảo trì và mở rộng.
![Layered Architecture](doc/SoDoKienTruc.drawio.svg)
*Hình 3.2: Mô hình kiến trúc phân lớp của My Silly Bestie*

### 1. Thông số kỹ thuật (Technical Stack)
* **Framework:** Spring Boot 4.0.6 (Latest/Snapshot).
* **Language:** Java JDK 21 (Tối ưu hóa với Virtual Threads).
* **Build Tool:** Maven.

### 2. Mô hình phân lớp
* **Lớp Hiển thị (Presentation Layer):** Xử lý giao diện, hiệu ứng hoạt họa và **phản hồi âm thanh tức thời**.
* **Lớp Kết nối (API Gateway):** Điều phối các yêu cầu từ người dùng đến các dịch vụ xử lý tương ứng.
* **Lớp Nghiệp vụ (Service Layer):** "Bộ não" của game, tính toán các chỉ số cảm xúc và quyết định phản ứng của thú cưng.
* **Lớp Dữ liệu (Data Access Layer):** Lưu trữ bền vững trạng thái của thú cưng và lịch sử tương tác.

---

## d. Mô tả Use Case đã chọn

Phần này trình bày đặc tả chi tiết cho các luồng nghiệp vụ chính của hệ thống, được phân nhóm theo quy trình tương tác của người chơi.

### 1. Nhóm Use Case 1 & 2: Thiết lập phiên chơi (Setup Session)

Đây là bước khởi tạo, nơi người chơi xác định đối tượng và công cụ tương tác. Việc thiết kế tốt giai đoạn này đảm bảo tính mạch lạc cho luồng dữ liệu phía Backend.

#### Bảng đặc tả chi tiết:

| Thành phần | Nội dung mô tả |
| :--- | :--- |
| **Tên Use Case** | **UC1: Chọn thú cưng & UC2: Chọn dụng cụ** |
| **Tác nhân (Actor)** | Người chơi (Player) |
| **Mô tả tóm tắt** | Người chơi lựa chọn một nhân vật thú cưng từ danh sách và chọn một dụng cụ tương tác đi kèm. |
| **Tiền điều kiện** | Người chơi đã truy cập vào giao diện chính của ứng dụng. |
| **Luồng sự kiện chính** | 1. Hệ thống hiển thị danh sách các "Besties" hiện có.<br/>2. Người chơi chọn một thú cưng.<br/>3. Hệ thống hiển thị hoạt cảnh thú cưng và mở khóa bảng dụng cụ.<br/>4. Người chơi chọn dụng cụ tương tác.<br/>5. Hệ thống thay đổi hình dạng con trỏ chuột thành hình dụng cụ đó. |
| **Hậu điều kiện** | Trạng thái phiên chơi (Session) được xác lập, sẵn sàng cho tương tác. |
| **Ngoại lệ** | Người chơi thao tác sai thứ tự: Hệ thống hiển thị thông báo nhắc nhở chọn thú cưng trước khi chọn dụng cụ. |

---

### 2. Phân tích kỹ thuật triển khai (Technical Analysis)

Để đáp ứng yêu cầu về **Phản hồi cảm giác chân thực** và tối ưu hóa hiệu năng với **Java 21**, nhóm Use Case này được hiện thực hóa như sau:

#### A. Xử lý phía Frontend (Client-side)
* **Thay đổi trạng thái (State Management):** Sử dụng JavaScript để cập nhật biến `currentPet` và `currentTool` ngay lập tức trên UI.
* **Tùy biến con trỏ (Custom Cursor):** Áp dụng CSS `cursor: url('tool_path'), auto;` giúp người dùng nhận diện công cụ đang cầm trên tay một cách trực quan nhất.

#### B. Xử lý phía Backend (Server-side - Spring Boot 4)
* **RESTful API:** `AssetController` tiếp nhận yêu cầu và trả về metadata của thú cưng/dụng cụ từ Database.
* **Performance:** Tận dụng **Virtual Threads** của Java 21 để xử lý việc tải tài nguyên hình ảnh/âm thanh đồng thời, giảm thiểu độ trễ khi chuyển đổi giữa các nhân vật.
* **Cấu trúc dữ liệu:** Sử dụng `Map` hoặc `Enum` để quản lý danh sách dụng cụ hợp lệ cho từng thú cưng, đảm bảo tính đóng gói (Encapsulation) của nghiệp vụ.

#### C. Quy tắc nghiệp vụ (Business Rules)
* **Mở khóa theo cấp độ:** Một số dụng cụ cao cấp chỉ được kích hoạt khi người chơi chọn thú cưng có độ khó cao (như Alpaca 5⭐).
* **Đồng bộ hóa âm thanh:** Ngay khi dụng cụ được chọn, hệ thống chuẩn bị sẵn (pre-load) các tệp âm thanh tương ứng để sẵn sàng cho các hành động tại UC3 và UC4.
## e. Lược đồ Sequence cho Use Case đã chọn (UC1 + UC2)

Sơ đồ dưới đây mô tả trình tự tương tác từ lúc người chơi chọn nhân vật đến khi hệ thống sẵn sàng công cụ tương tác:

![Lược đồ Sequence](/doc/SoDoSeq.drawio.svg)
*Hình 3.3: Lược đồ Sequence của usecase chuẩn bị*

## f. Các sơ đồ liên quan Design (Sơ đồ Thiết kế)

### 1. Sơ đồ lớp tổng quát (Class Diagram)

Sơ đồ dưới đây trình bày các lớp chính trong hệ thống, tuân thủ nguyên lý thiết kế hướng đối tượng và kiến trúc phân lớp.
![Sơ đồ tổng quát](/doc/SoDoLop.drawio.svg)
*Hình 3.4: Sơ đồ tổng quát*

### 2. Mục tiêu nghiệp vụ (Business Objectives)

Dự án hướng tới việc tạo ra một sản phẩm phần mềm không chỉ ổn định về kỹ thuật mà còn mang lại giá trị cảm xúc thực tế cho người dùng:

* **Xây dựng sự gắn kết (Emotional Connection):** Người chơi cảm nhận được sự thân thiết thông qua các hành động vuốt ve, chải lông với **phản hồi cảm giác chân thực**.
* **Cơ chế tương tác vui nhộn:** Hiện thực hóa logic "Trêu đùa có chừng mực" (Prank/Troll) và các phản ứng đáp trả lém lỉnh, bất ngờ từ thú cưng.
* **Giao diện thân thiện:** Sử dụng màu sắc ấm áp, bối cảnh phòng khách hoặc sân vườn tại gia, tạo không gian thư giãn tối đa.
* **Tối ưu hóa trải nghiệm:** Đảm bảo hệ thống phản hồi tức thì dưới **100ms**, mang lại cảm giác mượt mà trong từng hành động click và kéo thả.

---

### 3. Quy trình tương tác (Business Process)

Quy trình nghiệp vụ của **My Silly Bestie** được thiết kế xoay quanh vòng lặp tương tác giữa Người chơi và Thú cưng, bao gồm 3 trạng thái chính:

#### 3.1. Trạng thái nghỉ (Idle)
Đây là trạng thái mặc định khi bắt đầu phiên chơi.
* **Hoạt động:** Thú cưng thực hiện các hành động tự do (ngủ, ngáp, nhìn quanh). 
* **Nghiệp vụ:** Hệ thống chờ đợi người chơi lựa chọn dụng cụ (chăm sóc hoặc trêu đùa) từ thanh công cụ để bắt đầu phiên tương tác.

#### 3.2. Chế độ Chăm sóc (Care Mode)
Kích hoạt khi người chơi thực hiện đúng các hành động theo nhu cầu của thú cưng (ví dụ: chải lông, sấy khô, trang trí).
* **Phản hồi:** Kích hoạt các **hiệu ứng âm thanh thư giãn sống động** và hoạt cảnh hài lòng của thú cưng.
* **Kết quả:** Chỉ số **Hạnh phúc (Happiness)** tăng lên. Khi đạt ngưỡng nhất định, người chơi sẽ mở khóa được các biểu cảm đặc biệt.

#### 3.3. Chế độ Trêu chọc (Silly Prank Mode)
Kích hoạt khi người chơi thực hiện các hành động "nghịch ngợm" vào các vùng nhạy cảm của thú cưng.
* **Cảnh báo:** Thanh chỉ số **Nghi ngờ (Suspicion)** sẽ rung lên và chuyển màu, biểu thị sự mất kiên nhẫn của thú cưng.
* **Kết quả (Penalty):** Nếu chỉ số Nghi ngờ vượt ngưỡng 100%, thú cưng sẽ thực hiện một "Phản ứng đáp trả" hài hước (ví dụ: cướp dụng cụ, "đá" người chơi khỏi màn hình). 
* **Kết thúc:** Phiên chơi hiện tại sẽ dừng lại (End Game) sau phản ứng của thú cưng, yêu cầu người chơi bắt đầu một phiên mới.
  
## g. Implement (Hiện thực hóa UC 1+2)
Hệ thống kết hợp mẫu thiết kế **DAO** để truy xuất dữ liệu và **DTO** để tối ưu hóa dữ liệu truyền tải về phía giao diện (Frontend).

### 1. Lớp Truy cập Dữ liệu (PetDAO.java)
Lớp này chịu trách nhiệm tương tác trực tiếp với cơ sở dữ liệu thông qua SQL thuần túy, đảm bảo tính minh bạch trong quản lý dữ liệu.

```java
@Repository
public class PetDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public PetEntity findPetById(String id) {
        String sql = "SELECT * FROM pets WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(PetEntity.class), id);
    }
}

### 2. Lớp Vận chuyển Dữ liệu (PetDTO.java - Java 21 Record)
Sử dụng Java 21 Record để tạo cấu trúc dữ liệu gọn nhẹ, chỉ chứa những thông tin cần thiết cho giao diện (Tên, Ảnh, Âm thanh), giúp giảm băng thông và tăng tốc độ phản hồi.

// DTO chỉ chứa thông tin cần thiết cho UC 1+2
public record PetDTO(String name, String imagePath, String voiceSfx) {}

### 3. Lớp Điều khiển (PetController.java)
Kết nối DAO và DTO để trả về kết quả tối ưu cho người chơi.

@GetMapping("/{id}")
public ResponseEntity<PetDTO> selectPet(@PathVariable String id) {
    PetEntity pet = petDAO.findPetById(id);
    // Chuyển đổi từ Entity sang DTO để gửi về Frontend
    PetDTO response = new PetDTO(pet.getName(), pet.getImagePath(), pet.getVoiceSfx());
    return ResponseEntity.ok(response);
}

### 4. Quản lý Tài nguyên Hình ảnh & Âm thanh

Toàn bộ tài nguyên tĩnh được lưu trữ trực tiếp trong thư mục dự án để đảm bảo tính đóng gói:

Đường dẫn: src/main/resources/static/assets/

Cơ chế gọi: Frontend nhận imagePath từ DTO (ví dụ: capy.png) và truy xuất qua URL /assets/images/pets/capy.png.
