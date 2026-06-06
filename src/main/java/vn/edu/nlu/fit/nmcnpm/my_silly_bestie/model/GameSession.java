package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.model;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

import java.io.Serializable;

/**
 * Lưu trữ trạng thái của một lượt chơi game trong HTTP Session.
 * Triển khai Serializable để đảm bảo Spring Session có thể lưu trữ đối tượng này.
 */
public class GameSession implements Serializable {

    private PetEntity currentPet;
    private ToolEntity selectedTool;
    private int happiness;
    private int suspicion;
    private String lastReaction;
    private boolean gameOver;
    private int gold = 150;
    private int totalScore = 0;
    private int affection = 60;

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
        this.gold = gold;
        this.affection = affection;
        this.totalScore = totalScore;
    }

    /**
     * Xử lý tương tác khi người chơi sử dụng một dụng cụ.
     * - Dụng cụ CARE: Tăng Happiness, giảm nhẹ Suspicion.
     * - Dụng cụ PRANK: Tăng Suspicion, tăng ít Happiness (vì thú cưng vẫn thích bị chú ý).
     * - Khi Suspicion >= 100: Game Over với phản ứng phạt.
     */
    public void applyInteraction(ToolEntity tool) {
        this.selectedTool = tool;

        boolean isUncomfortable = suspicion > 65 || happiness < 25;

        if (tool.isCare()) {
            if (isUncomfortable) {
                this.suspicion += 15;
                this.lastReaction = "angry";
                this.affection = Math.max(0, this.affection - 8);
            } else {
                this.happiness = Math.min(100, this.happiness + tool.getHappinessRate());
                this.suspicion = Math.max(0, this.suspicion - 8);
                this.affection = Math.min(100, this.affection + 6);
                this.gold += 5;
                this.totalScore += 10;
                this.lastReaction = "happy";
            }
        } else { // PRANK
            if (isUncomfortable) {
                this.suspicion = Math.min(100, this.suspicion + tool.getSuspicionRate() + 20);
                this.affection = Math.max(0, this.affection - 15);
                this.lastReaction = "very_angry";
            } else {
                this.suspicion = Math.min(100, this.suspicion + tool.getSuspicionRate());
                this.happiness = Math.min(100, this.happiness + tool.getHappinessRate());
                this.affection = Math.min(100, this.affection + 3);
                this.totalScore += 8;
                this.lastReaction = "suspicious";
            }
        }

        if (this.suspicion >= 100) {
            this.gameOver = true;
            this.lastReaction = "angry";
            this.gold = Math.max(0, this.gold - 30);
        }
    }
    public boolean isUncomfortable() {
        return suspicion >= 60 || happiness <= 30 || affection <= 25;
    }

    public String getMoodStatus() {
        if (suspicion >= 80) return "Rất khó chịu! Dừng ngay!";
        if (suspicion >= 60) return "Đang bực bội...";
        if (happiness > 75) return "Rất vui vẻ ❤️";
        return "Bình thường";
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
    public int getHappinessPercent() { return happiness; }
    public int getSuspicionPercent() { return suspicion; }
    public int getGold() { return gold; }
    public int getTotalScore() { return totalScore; }
    public int getAffection() { return affection; }
    public int getAffectionPercent() { return affection; }
}
