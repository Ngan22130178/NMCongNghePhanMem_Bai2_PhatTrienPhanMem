package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GameSession {
    private String sessionId;
    private String petId;
    private int currentSuspicion; // Chỉ số từ 0 - 100
    private boolean isGameOver;
    private LocalDateTime startTime;

    public GameSession(String petId) {
        this.sessionId = java.util.UUID.randomUUID().toString();
        this.petId = petId;
        this.currentSuspicion = 0;
        this.isGameOver = false;
        this.startTime = LocalDateTime.now();
    }
}