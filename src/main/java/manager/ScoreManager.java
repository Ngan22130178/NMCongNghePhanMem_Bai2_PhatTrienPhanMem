package manager;

public class ScoreManager {

    private int score = 0;

    public void addScore(int amount) {

        score += amount;
    }

    public int getScore() {

        return score;
    }

    public void reset() {

        score = 0;
    }
}