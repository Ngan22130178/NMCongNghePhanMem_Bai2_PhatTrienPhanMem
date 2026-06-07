package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.model;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;
import java.io.Serializable;

public class GameSession implements Serializable {

    private PetEntity currentPet;
    private ToolEntity selectedTool;
    private int happiness = 60;
    private int suspicion = 0;
    private int affection = 50;
    private int gold = 180;
    private int totalScore = 0;
    private String lastReaction = "idle";
    private boolean gameOver = false;

    public void startNewGame(PetEntity pet) {
        this.currentPet = pet;
        this.happiness = 60;
        this.suspicion = 0;
        this.affection = 50;
        this.gold = 180;
        this.totalScore = 0;
        this.lastReaction = "idle";
        this.gameOver = false;
    }

    public void applyInteraction(ToolEntity tool) {
        this.selectedTool = tool;
        boolean uncomfortable = isUncomfortable();

        if (tool.isCare()) {
            if (uncomfortable) {
                suspicion = Math.min(100, suspicion + 18);
                affection = Math.max(0, affection - 10);
                lastReaction = "angry";
            } else {
                happiness = Math.min(100, happiness + tool.getHappinessRate());
                suspicion = Math.max(0, suspicion - 10);
                affection = Math.min(100, affection + 8);
                gold += 8;
                totalScore += 12;
                lastReaction = "happy";
            }
        } else { // PRANK
            if (uncomfortable) {
                suspicion = Math.min(100, suspicion + tool.getSuspicionRate() + 25);
                affection = Math.max(0, affection - 18);
                lastReaction = "very_angry";
            } else {
                suspicion = Math.min(100, suspicion + tool.getSuspicionRate());
                happiness = Math.min(100, happiness + tool.getHappinessRate() / 2);
                affection = Math.min(100, affection + 4);
                totalScore += 7;
                lastReaction = "suspicious";
            }
        }

        if (suspicion >= 100) {
            gameOver = true;
            lastReaction = "angry";
            gold = Math.max(0, gold - 40);
        }
    }

    public boolean isUncomfortable() {
        return suspicion >= 55 || happiness <= 35 || affection <= 30;
    }

    public String getMoodStatus() {
        if (suspicion >= 80) return "😠 RẤT KHÓ CHỊU! DỪNG NGAY!";
        if (suspicion >= 55) return "😣 Đang bực bội...";
        if (happiness >= 80) return "🥰 Rất hạnh phúc!";
        return "🙂 Bình thường";
    }

    // ==================== GETTERS & SETTERS ====================
    public PetEntity getCurrentPet() { return currentPet; }
    public int getHappiness() { return happiness; }
    public int getSuspicion() { return suspicion; }
    public int getAffection() { return affection; }
    public int getGold() { return gold; }

    /** Method này đang thiếu */
    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getTotalScore() { return totalScore; }
    public String getLastReaction() { return lastReaction; }
    public boolean isGameOver() { return gameOver; }

    public int getHappinessPercent() { return happiness; }
    public int getSuspicionPercent() { return suspicion; }
    public int getAffectionPercent() { return affection; }
    public ToolEntity getSelectedTool() {
        return selectedTool;
    }
    public void setSelectedTool(ToolEntity selectedTool) {
        this.selectedTool = selectedTool;
    }
}