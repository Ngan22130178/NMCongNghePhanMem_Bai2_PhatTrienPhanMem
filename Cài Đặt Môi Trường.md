# HƯỚNG DẪN THIẾT LẬP MÔI TRƯỜNG PHÁT TRIỂN (HOST)
Dự án: My Silly Bestie

## 1. Yêu cầu hệ thống
- **IDE**: Visual Studio Code (VS Code).
- **Ngôn ngữ**: Java JDK 21 (LTS) - Tối ưu cho Virtual Threads.
- **Trình duyệt**: Google Chrome (Sử dụng DevTools F12 để debug Frontend).
- **Công cụ**: Maven (Quản lý thư viện Spring Boot).

## 2. Các tiện ích mở rộng (Extensions) cần thiết
Cài đặt các Extension sau trong VS Code để tối ưu hóa hiệu suất:
- **Extension Pack for Java (Microsoft)**: Hỗ trợ lập trình Spring Boot.
- **Spring Boot Extension Pack**: Debug và quản lý ứng dụng Spring Boot.
- **Live Server**: Xem thay đổi Frontend thời gian thực.
- **Prettier**: Định dạng code tự động, tuân thủ chuẩn báo cáo.
- **GitLens**: Quản lý lịch sử commit để theo dõi tiến độ công việc.

## 3. Quy trình cấu hình & làm việc
1. **Clone dự án**: `git clone [URL_REPO]`.
2. **Cấu hình JDK**: Mở Command Palette (Ctrl+Shift+P) -> `Java: Configure Java Runtime` -> Chọn JDK 21.
3. **Quản lý đóng góp (Insights)**: 
   - Mỗi khi hoàn thành một chức năng, thực hiện commit nhỏ: 
     `git add .`
     `git commit -m "Tên-Cá-Nhân: Nội dung thay đổi"`
   - Đẩy code lên thường xuyên để biểu đồ Insights trên GitHub ghi nhận đóng góp nhiều ngày.
