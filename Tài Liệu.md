# BÁO CÁO PHÁT TRIỂN PHẦN MỀM - GIAI ĐOẠN 2
**Dự án:** My Silly Bestie
**Người thực hiện:** [Họ tên của bạn]
**MSSV:** [Mã số của bạn]

---

## REVISION HISTORY
| Phiên bản | Ngày cập nhật | Nội dung thay đổi | Người thực hiện |
| :--- | :--- | :--- | :--- |
| V1.0 | 04/06/2026 | Khởi tạo tài liệu và hoàn thiện đặc tả UC1, UC2. | [Họ tên] |
| V1.1 | 04/06/2026 | Phát triển tiếp: Đặc tả UC5 (Xử lý hình phạt), UC6 (Kết thúc lượt), Test case, Sequence. | [Họ tên] |

---

## 1. Giới thiệu tổng quan về đề tài
My Silly Bestie là không gian mô phỏng tương tác giữa người chơi và thú cưng ảo. Giai đoạn 2 tập trung hiện thực hóa logic "Trêu đùa có chừng mực" (Silly Prank Mode), xử lý phản hồi cảm xúc của thú cưng khi đạt ngưỡng chịu đựng và quy trình kết thúc phiên chơi (End Game).

## 2. Lược đồ Use Case của nhóm
(Vui lòng đính kèm hình ảnh: `/doc/LuocDoUsecase.drawio.svg`)

## 3. Kiến trúc hệ thống (Physical View)
Dự án áp dụng mô hình kiến trúc phân lớp (Layered Architecture). Ở giai đoạn này, lớp **Service Layer** được mở rộng để xử lý các logic nghiệp vụ phức tạp của UC5 và UC6, đảm bảo tính đóng gói và dễ bảo trì.

## 4. Mô tả Usecase đã chọn (UC5 & UC6)
| Thành phần | Nội dung mô tả |
| :--- | :--- |
| **Tên Use Case** | **UC5: Xử lý hình phạt & UC6: Kết thúc lượt** |
| **Tác nhân** | Người chơi (Player), Thú cưng (System) |
| **Luồng sự kiện chính** | 1. Hệ thống giám sát chỉ số 'Suspicion'. 2. Nếu Suspicion >= 100%, hệ thống kích hoạt animation 'Penalty'. 3. Hệ thống hiển thị thông báo kết thúc phiên chơi. 4. Reset Session và trở về màn hình chính. |

## 5. Test case cho Use Case đã chọn
| ID | Kịch bản | Dữ liệu đầu vào | Kết quả mong đợi |
| :--- | :--- | :--- | :--- |
| TC-01 | Trigger Penalty | Suspicion = 100 | Thú cưng hiển thị animation "Angry" |
| TC-02 | Kết thúc lượt | Penalty hoàn tất | Chuyển trạng thái sang màn hình "Game Over" |

## 6. Lược đồ Sequence cho Use Case đã chọn
(Vui lòng đính kèm hình ảnh: Sơ đồ trình tự từ Player -> Controller -> PenaltyService -> UI)

## 7. Các giao diện (GUI/UI)
*   **Giao diện thông báo:** Cửa sổ pop-up hiển thị trạng thái "Angry" khi thanh Suspicion đạt ngưỡng tối đa.
*   **Giao diện kết thúc:** Màn hình "Game Over" hiển thị tổng thời gian tương tác và nút "Chơi lại".

## 8. Sơ đồ Class cả nhóm
(Vui lòng đính kèm hình ảnh: Sơ đồ bao gồm các lớp mới: `PenaltyService`, `GameSession`, `EndGameHandler`)

## 9. ERD
(Vui lòng đính kèm hình ảnh sơ đồ ERD đã cập nhật các bảng ghi nhận lịch sử tương tác/kết quả game nếu có)
