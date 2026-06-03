# HƯỚNG DẪN TRIỂN KHAI HỆ THỐNG (TARGET)
Dự án: My Silly Bestie

## 1. Mục đích
Hướng dẫn triển khai ứng dụng từ môi trường phát triển (Localhost) lên môi trường chạy thực tế (Production/Target).

## 2. Các bước triển khai (Khuyên dùng GitHub Pages)

### Bước 1: Chuẩn bị Source Code
- Đảm bảo file `index.html` nằm tại thư mục gốc.
- Đảm bảo các đường dẫn tĩnh (ảnh, CSS, JS) sử dụng đường dẫn tương đối (ví dụ: `./assets/images/...`).

### Bước 2: Đẩy code lên GitHub
Sử dụng Terminal trong VS Code (Ctrl + `):
```bash
git init
git add .
git commit -m "Deploy: Triển khai phiên bản ổn định"
git remote add origin [URL_REPO_CUA_BAN]
git push -u origin main
