package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.model;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

import java.io.Serializable;

/**
 * ============================================================
 * MODEL: GameSession – Lưu trạng thái phiên chơi trong HTTP Session
 * ============================================================
 * Lớp này triển khai Serializable để Spring Session có thể serialize
 * và lưu trữ đối tượng trong bộ nhớ HTTP Session.
 *
 * LIÊN QUAN ĐẾN:
 *  - UC3: Thực hiện Trêu chọc (Silly Prank Mode)
 *    → applyInteraction() xử lý logic khi dụng cụ PRANK được sử dụng
 *    → Tăng suspicion, cập nhật lastReaction="suspicious"
 *    → Khi suspicion >= 100: đặt gameOver=true, lastReaction="angry"
 *
 *  - UC4: Thực hiện Chăm sóc (Care Mode)
 *    → applyInteraction() xử lý logic khi dụng cụ CARE được sử dụng
 *    → Tăng happiness, giảm suspicion, cập nhật lastReaction="happy"
 * ============================================================
 */
public class GameSession implements Serializable {

    private PetEntity currentPet;      // Thú cưng đang được tương tác (UC1)
    private ToolEntity selectedTool;   // Dụng cụ người chơi đang cầm (UC2/UC3/UC4)
    private int happiness;             // Chỉ số Hạnh phúc (0 - 100) – tăng khi UC4
    private int suspicion;             // Chỉ số Nghi ngờ (0 - 100) – tăng khi UC3
    private String lastReaction;       // Phản ứng mới nhất: "idle"|"happy"|"suspicious"|"angry"
    private boolean gameOver;          // true khi suspicion >= 100 (UC3 kết thúc bằng Penalty)

    public GameSession() {
        this.happiness = 50;
        this.suspicion = 0;
        this.gameOver = false;
        this.lastReaction = "idle";
    }

    /**
     * Khởi tạo phiên chơi mới với thú cưng được chọn (UC1).
     * Reset toàn bộ chỉ số về trạng thái ban đầu.
     */
    public void startNewGame(PetEntity pet) {
        this.currentPet = pet;
        this.selectedTool = null;
        this.happiness = 50;
        this.suspicion = 0;
        this.gameOver = false;
        this.lastReaction = "idle";
    }

    /**
     * ============================================================
     * [UC3 – THỰC HIỆN TRÊU CHỌC] & [UC4 – THỰC HIỆN CHĂM SÓC]
     * ============================================================
     * Xử lý tương tác khi người chơi sử dụng một dụng cụ.
     *
     * Logic UC4 (CARE – Chăm sóc):
     *  - Tăng happiness += tool.happinessRate (tối đa 100)
     *  - Giảm suspicion -= 5 (tối thiểu 0) → chăm sóc làm dịu nghi ngờ
     *  - Đặt lastReaction = "happy" → frontend hiển thị animation vui + âm thanh ding
     *
     * Logic UC3 (PRANK – Trêu chọc):
     *  - Tăng suspicion += tool.suspicionRate (tối đa 100)
     *  - happiness += tool.happinessRate (nhỏ, vì thú cưng vẫn thích bị chú ý)
     *  - Đặt lastReaction = "suspicious" → frontend hiển thị animation lắc + âm thanh boing
     *  - Nếu suspicion >= 100: gameOver = true, lastReaction = "angry"
     *    → GameController redirect đến /game/penalty (UC6)
     *
     * @param tool Dụng cụ người chơi đã chọn (CARE hoặc PRANK)
     */
    public void applyInteraction(ToolEntity tool) {
        this.selectedTool = tool;

        // ── UC4: Chăm sóc thú cưng ──────────────────────────────────────
        if (tool.isCare()) {
            // Tăng hạnh phúc, giới hạn tối đa 100
            this.happiness = Math.min(100, this.happiness + tool.getHappinessRate());
            // Chăm sóc làm dịu nghi ngờ (giảm 5 điểm, không âm)
            this.suspicion = Math.max(0, this.suspicion - 5);
            // Phản ứng: vui mừng → frontend: animation bounce + confetti + âm thanh ding
            this.lastReaction = "happy";

        // ── UC3: Trêu chọc thú cưng ─────────────────────────────────────
        } else { // tool.isPrank()
            // Tăng nghi ngờ, giới hạn tối đa 100
            this.suspicion = Math.min(100, this.suspicion + tool.getSuspicionRate());
            // Nhỏ +happiness vì thú cưng vẫn thích được chú ý (dù là trêu)
            this.happiness = Math.max(0, this.happiness + tool.getHappinessRate());
            // Phản ứng: nghi ngờ → frontend: animation lắc + âm thanh boing
            this.lastReaction = "suspicious";
        }

        // ── UC3 kết thúc: Game Over khi Suspicion >= 100% ────────────────
        if (this.suspicion >= 100) {
            this.gameOver = true;
            // Phản ứng đáp trả: thú cưng nổi giận → redirect /game/penalty
            this.lastReaction = "angry";
        }
    }

    // ===== Getters & Setters =====
    public PetEntity getCurrentPet() { return currentPet; }
    public void setCurrentPet(PetEntity currentPet) { this.currentPet = currentPet; }

    public ToolEntity getSelectedTool() { return selectedTool; }
    public void setSelectedTool(ToolEntity selectedTool) { this.selectedTool = selectedTool; }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = happiness; }

    public int getSuspicion() { return suspicion; }
    public void setSuspicion(int suspicion) { this.suspicion = suspicion; }

    public String getLastReaction() { return lastReaction; }
    public void setLastReaction(String lastReaction) { this.lastReaction = lastReaction; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    /** Tính % tỷ lệ thanh Happiness để CSS width */
    public int getHappinessPercent() { return happiness; }

    /** Tính % tỷ lệ thanh Suspicion để CSS width */
    public int getSuspicionPercent() { return suspicion; }
}
