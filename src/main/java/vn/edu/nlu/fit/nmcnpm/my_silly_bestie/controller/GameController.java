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
 * ============================================================
 * CONTROLLER: GameController – Điều phối toàn bộ luồng game
 * ============================================================
 * Đây là lớp Controller MVC chính, tiếp nhận mọi HTTP Request
 * từ trình duyệt và trả về tên template Thymeleaf để render.
 *
 * ROUTE MAP:
 *  GET  /                → UC1: Hiển thị danh sách thú cưng
 *  GET  /game?petId=...  → UC1+UC2: Bắt đầu phiên chơi
 *  POST /game/interact   → UC3 & UC4: Xử lý tương tác (CARE hoặc PRANK)
 *  GET  /game/play       → Hiển thị lại game sau tương tác
 *  GET  /game/penalty    → UC3 kết thúc: Màn hình phạt khi thua
 *  GET  /game/restart    → UC7: Reset và chọn thú cưng lại
 * ============================================================
 */
@Controller
public class GameController {

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private ToolDAO toolDAO;

    // ============================================================
    // [UC1] GET / → Trang chủ: Hiển thị danh sách thú cưng để chọn
    // ============================================================
    /**
     * UC1 – Chọn thú cưng:
     * Truy vấn toàn bộ danh sách pets từ H2 Database qua PetDAO,
     * đưa vào Model để Thymeleaf render thành lưới pet-card trên index.html.
     */
    @GetMapping("/")
    public String index(Model model) {
        List<PetEntity> pets = petDAO.findAll();
        model.addAttribute("pets", pets);
        model.addAttribute("pageTitle", "My Silly Bestie – Chọn Người Bạn Của Bạn!");
        return "index"; // → src/main/resources/templates/index.html
    }

    // ============================================================
    // [UC1+UC2] GET /game?petId=xxx → Bắt đầu lượt chơi
    // ============================================================
    /**
     * UC1+UC2 – Khởi tạo phiên chơi:
     * 1. Tìm thú cưng theo petId từ database
     * 2. Tạo mới GameSession và gắn vào HTTP Session
     * 3. Tải danh sách tools (UC2 – chuẩn bị bảng dụng cụ)
     * 4. Trả về template game.html với đầy đủ dữ liệu
     */
    @GetMapping("/game")
    public String startGame(@RequestParam String petId, HttpSession session, Model model) {
        PetEntity pet = petDAO.findById(petId);
        if (pet == null) {
            return "redirect:/"; // Quay lại trang chủ nếu không tìm thấy thú cưng
        }

        // Khởi tạo hoặc reset GameSession (UC1 hoàn thành)
        GameSession gameSession = new GameSession();
        gameSession.startNewGame(pet);
        session.setAttribute("gameSession", gameSession);

        // UC2: Tải danh sách dụng cụ để hiển thị Tool Shelf
        List<ToolEntity> tools = toolDAO.findAll();
        model.addAttribute("gameSession", gameSession);
        model.addAttribute("tools", tools);
        model.addAttribute("pageTitle", "My Silly Bestie – Chơi cùng " + pet.getName());
        return "game"; // → src/main/resources/templates/game.html
    }

    // ============================================================
    // [UC3 & UC4] POST /game/interact → Người chơi dùng dụng cụ tương tác
    // ============================================================
    /**
     * *** PHẦN THỰC HIỆN CHÍNH CỦA UC3 (TRÊU CHỌC) VÀ UC4 (CHĂM SÓC) ***
     *
     * Luồng xử lý:
     * 1. Lấy GameSession từ HTTP Session (kiểm tra hợp lệ)
     * 2. Tìm ToolEntity theo toolId từ database
     * 3. Gọi gameSession.applyInteraction(tool):
     *    - Nếu tool.isCare()  → UC4: Tăng Happiness, giảm Suspicion
     *    - Nếu tool.isPrank() → UC3: Tăng Suspicion (nếu >= 100: gameOver=true)
     * 4. Lưu GameSession đã cập nhật vào HTTP Session
     * 5. Kiểm tra gameOver:
     *    - true  → UC3 kết thúc: redirect /game/penalty
     *    - false → tiếp tục: redirect /game/play
     *
     * @param toolId    ID của dụng cụ người chơi chọn ("comb","dryer","feather","spray")
     * @param session   HTTP Session chứa GameSession hiện tại
     */
    @PostMapping("/game/interact")
    public String interact(@RequestParam String toolId, HttpSession session,
                           RedirectAttributes redirectAttributes) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");

        // Kiểm tra session hợp lệ – tránh NullPointerException
        if (gameSession == null || gameSession.getCurrentPet() == null) {
            return "redirect:/"; // Mất session → về trang chủ
        }

        // Tìm dụng cụ trong database
        ToolEntity tool = toolDAO.findById(toolId);
        if (tool != null) {
            // [UC3/UC4] Áp dụng logic tương tác (xem GameSession.applyInteraction())
            gameSession.applyInteraction(tool);
            // Lưu lại trạng thái session đã cập nhật
            session.setAttribute("gameSession", gameSession);
        }

        // [UC3] Kiểm tra điều kiện Game Over (Suspicion >= 100%)
        if (gameSession.isGameOver()) {
            return "redirect:/game/penalty"; // → Màn hình phạt
        }

        return "redirect:/game/play"; // → Tiếp tục chơi
    }

    // ============================================================
    // GET /game/play → Trang game sau khi tương tác (redirect target)
    // ============================================================
    /**
     * Sau khi POST /game/interact xử lý xong, redirect đến đây để
     * render lại game.html với trạng thái mới (PRG Pattern).
     * Tránh form re-submission khi người dùng nhấn F5.
     */
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
    // [UC3 kết thúc] GET /game/penalty → Màn hình phạt khi Suspicion >= 100%
    // ============================================================
    /**
     * UC3 – Kết quả Penalty:
     * Hiển thị màn hình "Oops!" khi thú cưng nổi giận.
     * Giao diện hiển thị tổng kết happiness/suspicion cuối cùng
     * và 2 nút: Chơi lại / Chọn thú cưng khác.
     */
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
    // [UC7] GET /game/restart → Xóa session và chọn thú cưng lại từ đầu
    // ============================================================
    /**
     * UC7 – Chơi lại:
     * Xóa GameSession khỏi HTTP Session để reset hoàn toàn trạng thái game,
     * sau đó redirect về trang chủ để người chơi chọn thú cưng mới.
     */
    @GetMapping("/game/restart")
    public String restart(HttpSession session) {
        session.removeAttribute("gameSession");
        return "redirect:/";
    }
}
