package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

import static org.junit.jupiter.api.Assertions.*;

class GameSessionTest {

    private GameSession gameSession;
    private PetEntity samplePet;

    @BeforeEach
    void setUp() {
        gameSession = new GameSession();
        samplePet = new PetEntity("cat", "Mèo Cam", "Mô tả", "img/cat.png", "sfx/cat.mp3", 2);
    }

    @Test
    void testInitialState() {
        System.out.println("\n>>> Running: testInitialState");
        System.out.println("Asserting initial values... (Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion() + ")");
        assertEquals(50, gameSession.getHappiness());
        assertEquals(0, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());
        assertEquals("idle", gameSession.getLastReaction());
        System.out.println("[SUCCESS] testInitialState passed!");
    }

    @Test
    void testStartNewGame() {
        System.out.println("\n>>> Running: testStartNewGame");
        gameSession.setHappiness(80);
        gameSession.setSuspicion(40);
        gameSession.setGameOver(true);
        gameSession.setLastReaction("angry");
        System.out.println("Configured mock game state (Happiness: 80, Suspicion: 40, GameOver: true)");

        gameSession.startNewGame(samplePet);
        System.out.println("Started new game with pet: " + samplePet.getName());

        assertEquals(samplePet, gameSession.getCurrentPet());
        assertEquals(50, gameSession.getHappiness());
        assertEquals(0, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());
        assertEquals("idle", gameSession.getLastReaction());
        assertNull(gameSession.getSelectedTool());
        System.out.println("[SUCCESS] testStartNewGame passed! Game state reset to defaults.");
    }

    @Test
    void testApplyInteraction_CareTool() {
        System.out.println("\n>>> Running: testApplyInteraction_CareTool");
        gameSession.startNewGame(samplePet);
        ToolEntity careTool = new ToolEntity("comb", "Lược chải", "Chải lông", "img/comb.png", "CARE", 0, 15);
        gameSession.setSuspicion(20);
        System.out.println("Before Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion());

        gameSession.applyInteraction(careTool);
        System.out.println("Applied CARE Tool: " + careTool.getName() + " (+15 Happiness, -5 Suspicion)");
        System.out.println("After Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion() + ", Reaction: " + gameSession.getLastReaction());

        assertEquals(65, gameSession.getHappiness());
        assertEquals(15, gameSession.getSuspicion());
        assertEquals("happy", gameSession.getLastReaction());
        assertFalse(gameSession.isGameOver());
        assertEquals(careTool, gameSession.getSelectedTool());
        System.out.println("[SUCCESS] testApplyInteraction_CareTool passed!");
    }

    @Test
    void testApplyInteraction_CareTool_Victory() {
        System.out.println("\n>>> Running: testApplyInteraction_CareTool_Victory");
        gameSession.startNewGame(samplePet);
        gameSession.setHappiness(85);
        ToolEntity careTool = new ToolEntity("dryer", "Máy sấy", "Chăm sóc", "img/dryer.png", "CARE", 0, 20);
        System.out.println("Before Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion());

        gameSession.applyInteraction(careTool);
        System.out.println("Applied CARE Tool: " + careTool.getName() + " (+20 Happiness)");
        System.out.println("After Interaction - Happiness: " + gameSession.getHappiness() + ", Victory: " + gameSession.isVictory() + ", Reaction: " + gameSession.getLastReaction());

        assertEquals(100, gameSession.getHappiness());
        assertTrue(gameSession.isVictory());
        assertEquals("victory", gameSession.getLastReaction());
        assertFalse(gameSession.isGameOver());
        System.out.println("[SUCCESS] testApplyInteraction_CareTool_Victory passed! Victory triggered successfully.");
    }

    @Test
    void testApplyInteraction_CareTool_Limits() {
        System.out.println("\n>>> Running: testApplyInteraction_CareTool_Limits");
        gameSession.startNewGame(samplePet);
        ToolEntity careTool = new ToolEntity("comb", "Lược chải", "Chải lông", "img/comb.png", "CARE", 0, 60);
        System.out.println("Before Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion());

        gameSession.applyInteraction(careTool);
        System.out.println("Applied CARE Tool with high value: +60 Happiness");
        System.out.println("After Interaction - Happiness: " + gameSession.getHappiness() + " (Expected limit 100), Suspicion: " + gameSession.getSuspicion() + " (Expected limit 0)");

        assertEquals(100, gameSession.getHappiness());
        assertEquals(0, gameSession.getSuspicion());
        System.out.println("[SUCCESS] testApplyInteraction_CareTool_Limits passed! Boundary values capped correctly.");
    }

    @Test
    void testApplyInteraction_PrankTool() {
        System.out.println("\n>>> Running: testApplyInteraction_PrankTool");
        gameSession.startNewGame(samplePet);
        ToolEntity prankTool = new ToolEntity("feather", "Lông vũ", "Trêu chọc", "img/feather.png", "PRANK", 30, 5);
        System.out.println("Before Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion());

        gameSession.applyInteraction(prankTool);
        System.out.println("Applied PRANK Tool: " + prankTool.getName() + " (+30 Suspicion, +5 Happiness)");
        System.out.println("After Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion() + ", Reaction: " + gameSession.getLastReaction());

        assertEquals(30, gameSession.getSuspicion());
        assertEquals(55, gameSession.getHappiness());
        assertEquals("suspicious", gameSession.getLastReaction());
        assertFalse(gameSession.isGameOver());
        System.out.println("[SUCCESS] testApplyInteraction_PrankTool passed!");
    }

    @Test
    void testApplyInteraction_PrankTool_GameOver() {
        System.out.println("\n>>> Running: testApplyInteraction_PrankTool_GameOver");
        gameSession.startNewGame(samplePet);
        ToolEntity strongPrank = new ToolEntity("water_spray", "Bình xịt nước", "Trêu mạnh", "img/spray.png", "PRANK", 40, -10);
        System.out.println("Before Interaction - Happiness: " + gameSession.getHappiness() + ", Suspicion: " + gameSession.getSuspicion());

        // Lần 1
        gameSession.applyInteraction(strongPrank);
        System.out.println("Lần 1: Dùng " + strongPrank.getName() + " -> Suspicion: " + gameSession.getSuspicion() + ", GameOver: " + gameSession.isGameOver());
        assertEquals(40, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());

        // Lần 2
        gameSession.applyInteraction(strongPrank);
        System.out.println("Lần 2: Dùng " + strongPrank.getName() + " -> Suspicion: " + gameSession.getSuspicion() + ", GameOver: " + gameSession.isGameOver());
        assertEquals(80, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());

        // Lần 3
        gameSession.applyInteraction(strongPrank);
        System.out.println("Lần 3: Dùng " + strongPrank.getName() + " -> Suspicion: " + gameSession.getSuspicion() + ", GameOver: " + gameSession.isGameOver());
        assertEquals(100, gameSession.getSuspicion());
        assertTrue(gameSession.isGameOver());
        assertEquals("angry", gameSession.getLastReaction());
        System.out.println("[SUCCESS] testApplyInteraction_PrankTool_GameOver passed! Game Over triggered successfully.");
    }
}
