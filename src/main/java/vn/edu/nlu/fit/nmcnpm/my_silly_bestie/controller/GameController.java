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

/**
 * Controller MVC chính của game.
 * Điều phối tất cả các yêu cầu từ trình duyệt và trả về tên template Thymeleaf.
 */
@Controller
public class GameController {

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private ToolDAO toolDAO;

    // ============================================================
    // GET / → Trang chủ: Hiển thị danh sách thú cưng để chọn
    // ============================================================
    @GetMapping("/")
    public String index(Model model) {
        List<PetEntity> pets = petDAO.findAll();
        model.addAttribute("pets", pets);
        model.addAttribute("pageTitle", "My Silly Bestie – Chọn Người Bạn Của Bạn!");
        return "index"; // → src/main/resources/templates/index.html
    }

    // ============================================================
    // GET /game?petId=xxx → Bắt đầu lượt chơi với thú cưng đã chọn
    // ============================================================
    @GetMapping("/game")
    public String startGame(@RequestParam String petId, HttpSession session, Model model) {
        PetEntity pet = petDAO.findById(petId);
        if (pet == null) {
            return "redirect:/"; // Quay lại trang chủ nếu không tìm thấy thú cưng
        }

        // Khởi tạo hoặc reset session game
        GameSession gameSession = new GameSession();
        gameSession.startNewGame(pet);
        session.setAttribute("gameSession", gameSession);

        List<ToolEntity> tools = toolDAO.findAll();
        model.addAttribute("gameSession", gameSession);
        model.addAttribute("tools", tools);
        model.addAttribute("pageTitle", "My Silly Bestie – Chơi cùng " + pet.getName());
        return "game"; // → src/main/resources/templates/game.html
    }

    // ============================================================
    // POST /game/interact → Người chơi dùng dụng cụ tương tác
    // ============================================================
    @PostMapping("/game/interact")
    public String interact(@RequestParam String toolId, HttpSession session,
                           RedirectAttributes redirectAttributes) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");

        // Kiểm tra session hợp lệ
        if (gameSession == null || gameSession.getCurrentPet() == null) {
            return "redirect:/"; // Mất session → về trang chủ
        }

        ToolEntity tool = toolDAO.findById(toolId);
        if (tool != null) {
            gameSession.applyInteraction(tool);
            session.setAttribute("gameSession", gameSession);
        }

        if (gameSession.isGameOver()) {
            return "redirect:/game/penalty";
        }

        return "redirect:/game/play";
    }

    // ============================================================
    // GET /game/play → Trang game sau khi tương tác (redirect target)
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
        model.addAttribute("pageTitle", "My Silly Bestie – Chơi cùng " + gameSession.getCurrentPet().getName());
        return "game"; // Dùng lại template game.html
    }

    // ============================================================
    // GET /game/penalty → Màn hình phạt khi Suspicion >= 100%
    // ============================================================
    @GetMapping("/game/penalty")
    public String penalty(HttpSession session, Model model) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null || gameSession.getCurrentPet() == null) {
            return "redirect:/";
        }
        model.addAttribute("gameSession", gameSession);
        model.addAttribute("pageTitle", "My Silly Bestie – Oops! Bạn bị phạt rồi!");
        return "penalty"; // → src/main/resources/templates/penalty.html
    }

    // ============================================================
    // GET /game/restart → Xóa session và chọn thú cưng lại từ đầu
    // ============================================================
    @GetMapping("/game/restart")
    public String restart(HttpSession session) {
        session.removeAttribute("gameSession");
        return "redirect:/";
    }
}
