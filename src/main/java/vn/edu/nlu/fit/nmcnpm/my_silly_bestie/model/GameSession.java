package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.model;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

import java.io.Serializable;

/**
 * Lưu trữ trạng thái của một lượt chơi game trong HTTP Session.
 * Triển khai Serializable để đảm bảo Spring Session có thể lưu trữ đối tượng này.
 */
public class GameSession implements Serializable {

    private PetEntity currentPet;      // Thú cưng đang được tương tác
    private ToolEntity selectedTool;   // Dụng cụ người chơi đang cầm
    private int happiness;             // Chỉ số Hạnh phúc (0 - 100)
    private int suspicion;             // Chỉ số Nghi ngờ (0 - 100)
    private String lastReaction;       // Phản ứng mới nhất của thú cưng để hiển thị hoạt ảnh
    private boolean gameOver;          // Đánh dấu lượt chơi đã kết thúc hay chưa

    public GameSession() {
        this.happiness = 50;
        this.suspicion = 0;
        this.gameOver = false;
        this.lastReaction = "idle";
    }

    // Khởi tạo phiên chơi mới với thú cưng được chọn
    public void startNewGame(PetEntity pet) {
        this.currentPet = pet;
        this.selectedTool = null;
        this.happiness = 50;
        this.suspicion = 0;
        this.gameOver = false;
        this.lastReaction = "idle";
    }

    /**
     * Xử lý tương tác khi người chơi sử dụng một dụng cụ.
     * - Dụng cụ CARE: Tăng Happiness, giảm nhẹ Suspicion.
     * - Dụng cụ PRANK: Tăng Suspicion, tăng ít Happiness (vì thú cưng vẫn thích bị chú ý).
     * - Khi Suspicion >= 100: Game Over với phản ứng phạt.
     */
    public void applyInteraction(ToolEntity tool) {
        this.selectedTool = tool;

        if (tool.isCare()) {
            this.happiness = Math.min(100, this.happiness + tool.getHappinessRate());
            this.suspicion = Math.max(0, this.suspicion - 5); // Chăm sóc làm dịu nghi ngờ
            this.lastReaction = "happy";
        } else { // PRANK
            this.suspicion = Math.min(100, this.suspicion + tool.getSuspicionRate());
            this.happiness = Math.max(0, this.happiness + tool.getHappinessRate());
            this.lastReaction = "suspicious";
        }

        if (this.suspicion >= 100) {
            this.gameOver = true;
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
