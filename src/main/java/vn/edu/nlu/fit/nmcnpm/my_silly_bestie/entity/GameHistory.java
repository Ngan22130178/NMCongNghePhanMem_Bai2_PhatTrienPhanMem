package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity;

import lombok.Data;

@Data
public class GameHistory {
    private String sessionId;
    private String result; // "WIN" hoặc "LOSE"
    private int finalSuspicion;
}