package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto;

/**
 * Cập nhật DTO để hỗ trợ luồng UC5 & UC6
 * @param isPenalty: True nếu đã đạt ngưỡng 100% (cần kích hoạt hình phạt)
 * @param currentSuspicion: Giá trị hiện tại của thanh cảnh giác
 * @param message: Thông báo hiển thị cho người chơi
 * @param triggerBlackScreen: Cờ báo hiệu Frontend bật hiệu ứng màn hình đen (UC5)
 * @param gameOver: Cờ báo hiệu kết thúc lượt chơi (UC6)
 */
public record PenaltyResponseDTO(
    boolean isPenalty,
    int currentSuspicion,
    String message,
    boolean triggerBlackScreen, 
    boolean gameOver
) {}