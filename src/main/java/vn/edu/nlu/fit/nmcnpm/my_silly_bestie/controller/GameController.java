package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.PetDAO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.ToolDAO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.model.GameSession;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private ToolDAO toolDAO;

    // ============================================================
    // GET / → Trang chủ
    // ============================================================
    @GetMapping("/")
    public String index(Model model) {
        List<PetEntity> pets = petDAO.findAll();
        model.addAttribute("pets", pets);
        model.addAttribute("pageTitle", "My Silly Bestie – Chọn Thú Cưng");
        return "index";
    }

    // ============================================================
    // GET /game → Bắt đầu chơi
    // ============================================================
    @GetMapping("/game")
    public String startGame(@RequestParam String petId, HttpSession session, Model model) {
        PetEntity pet = petDAO.findById(petId);
        if (pet == null) {
            return "redirect:/";
        }

        GameSession gameSession = new GameSession();
        gameSession.startNewGame(pet);
        session.setAttribute("gameSession", gameSession);

        List<ToolEntity> tools = toolDAO.findAll();
        model.addAttribute("gameSession", gameSession);
        model.addAttribute("tools", tools);
        model.addAttribute("pageTitle", "Chơi cùng " + pet.getName());
        return "game";
    }

    // ============================================================
    // POST /game/interact → Tương tác (CHĂM SÓC / TRÊU CHỌC)
    // ============================================================
    @PostMapping("/game/interact")
    public String interact(@RequestParam String toolId, HttpSession session, RedirectAttributes ra) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null || gameSession.getCurrentPet() == null) {
            return "redirect:/";
        }

        ToolEntity tool = toolDAO.findById(toolId);
        if (tool != null) {
            gameSession.applyInteraction(tool);
            session.setAttribute("gameSession", gameSession);

            if (gameSession.isGameOver()) {
                return "redirect:/game/penalty";
            }
        }
        return "redirect:/game/play";
    }

    // ============================================================
    // POST /game/buy → Mua đồ
    // ============================================================
    @PostMapping("/game/buy")
    public String buyItem(@RequestParam String toolId, HttpSession session, RedirectAttributes ra) {
        GameSession gs = (GameSession) session.getAttribute("gameSession");
        if (gs == null) return "redirect:/";

        ToolEntity tool = toolDAO.findById(toolId);
        if (tool != null && gs.getGold() >= 35) {
            gs.setGold(gs.getGold() - 35);
            ra.addFlashAttribute("success", "✅ Đã mua " + tool.getName());
        } else {
            ra.addFlashAttribute("error", "❌ Không đủ vàng!");
        }
        return "redirect:/game/play";
    }

    // ============================================================
    // GET /game/play → Hiển thị lại màn hình game
    // ============================================================
    @GetMapping("/game/play")
    public String playGame(HttpSession session, Model model) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null || gameSession.getCurrentPet() == null) {
            return "redirect:/";
        }

        List<ToolEntity> tools = toolDAO.findAll();
        model.addAttribute("gameSession", gameSession);
        model.addAttribute("tools", tools);
        return "game";
    }

    // ============================================================
    // GET /game/penalty
    // ============================================================
    @GetMapping("/game/penalty")
    public String penalty(HttpSession session, Model model) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null) return "redirect:/";

        model.addAttribute("gameSession", gameSession);
        return "penalty";
    }

    // ============================================================
    // GET /game/restart
    // ============================================================
    @GetMapping("/game/restart")
    public String restart(HttpSession session) {
        session.removeAttribute("gameSession");
        return "redirect:/";
    }
}