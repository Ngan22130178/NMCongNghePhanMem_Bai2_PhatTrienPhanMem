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
        assertEquals(50, gameSession.getHappiness());
        assertEquals(0, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());
        assertEquals("idle", gameSession.getLastReaction());
    }

    @Test
    void testStartNewGame() {
        gameSession.setHappiness(80);
        gameSession.setSuspicion(40);
        gameSession.setGameOver(true);
        gameSession.setLastReaction("angry");

        gameSession.startNewGame(samplePet);

        assertEquals(samplePet, gameSession.getCurrentPet());
        assertEquals(50, gameSession.getHappiness());
        assertEquals(0, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());
        assertEquals("idle", gameSession.getLastReaction());
        assertNull(gameSession.getSelectedTool());
    }

    @Test
    void testApplyInteraction_CareTool() {
        gameSession.startNewGame(samplePet);
        // Dụng cụ chăm sóc: Tăng 15 hạnh phúc, isCare = true
        ToolEntity careTool = new ToolEntity("comb", "Lược chải", "Chải lông", "img/comb.png", "CARE", 0, 15);

        // Giả sử nghi ngờ ban đầu đang ở mức 20
        gameSession.setSuspicion(20);

        gameSession.applyInteraction(careTool);

        // Hạnh phúc tăng: 50 + 15 = 65
        assertEquals(65, gameSession.getHappiness());
        // Nghi ngờ giảm đi 5: 20 - 5 = 15
        assertEquals(15, gameSession.getSuspicion());
        // Phản ứng phải là "happy"
        assertEquals("happy", gameSession.getLastReaction());
        assertFalse(gameSession.isGameOver());
        assertEquals(careTool, gameSession.getSelectedTool());
    }

    @Test
    void testApplyInteraction_CareTool_Limits() {
        gameSession.startNewGame(samplePet);
        ToolEntity careTool = new ToolEntity("comb", "Lược chải", "Chải lông", "img/comb.png", "CARE", 0, 60);

        gameSession.applyInteraction(careTool);

        // Hạnh phúc tăng: 50 + 60 = 110 -> Giới hạn tối đa 100
        assertEquals(100, gameSession.getHappiness());
        // Nghi ngờ ban đầu là 0 -> Giảm 5 -> Giới hạn tối thiểu 0
        assertEquals(0, gameSession.getSuspicion());
    }

    @Test
    void testApplyInteraction_PrankTool() {
        gameSession.startNewGame(samplePet);
        // Dụng cụ trêu chọc: tăng 30 nghi ngờ, tăng 5 hạnh phúc (vì thú thích được chú ý), isCare = false
        ToolEntity prankTool = new ToolEntity("feather", "Lông vũ", "Trêu chọc", "img/feather.png", "PRANK", 30, 5);

        gameSession.applyInteraction(prankTool);

        // Nghi ngờ tăng: 0 + 30 = 30
        assertEquals(30, gameSession.getSuspicion());
        // Hạnh phúc tăng: 50 + 5 = 55
        assertEquals(55, gameSession.getHappiness());
        // Phản ứng là "suspicious"
        assertEquals("suspicious", gameSession.getLastReaction());
        assertFalse(gameSession.isGameOver());
    }

    @Test
    void testApplyInteraction_PrankTool_GameOver() {
        gameSession.startNewGame(samplePet);
        ToolEntity strongPrank = new ToolEntity("water_spray", "Bình xịt nước", "Trêu mạnh", "img/spray.png", "PRANK", 40, -10);

        // Lần 1: Nghi ngờ tăng 40
        gameSession.applyInteraction(strongPrank);
        assertEquals(40, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());

        // Lần 2: Nghi ngờ tăng 80
        gameSession.applyInteraction(strongPrank);
        assertEquals(80, gameSession.getSuspicion());
        assertFalse(gameSession.isGameOver());

        // Lần 3: Nghi ngờ tăng 120 -> Giới hạn tối đa 100, Game Over kích hoạt
        gameSession.applyInteraction(strongPrank);
        assertEquals(100, gameSession.getSuspicion());
        assertTrue(gameSession.isGameOver());
        assertEquals("angry", gameSession.getLastReaction());
    }
}
