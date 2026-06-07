package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.service;
import org.springframework.stereotype.Service;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.PenaltyResponseDTO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.PrankRequest;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.GameHistory;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.GameSession;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.HistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PenaltyService {
    @Autowired
    private SessionManager sessionManager;

    public PenaltyResponseDTO processInteraction(PrankRequest request) {
        // 5.1.1: Ghi nhận và kiểm tra ngưỡng
        int currentSuspicion = calculateSuspicion(request.petId());
        
        if (currentSuspicion >= 100) {
            // 5.2: Vô hiệu hóa input cho phiên chơi
            sessionManager.disableInteractionFlags(request.sessionId());
            
            // Trả về DTO với các cờ hiệu (5.3, 6.1, 6.4)
            return new PenaltyResponseDTO(
                true, 100, "Bạn đã thua!", true, true
            );
        }
        return new PenaltyResponseDTO(false, currentSuspicion, "Pet đang nghi ngờ...", false, false);
    }
}