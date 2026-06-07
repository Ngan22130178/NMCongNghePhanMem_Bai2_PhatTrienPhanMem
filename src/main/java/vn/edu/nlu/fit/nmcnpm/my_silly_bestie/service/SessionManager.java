package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.service;
import org.springframework.stereotype.Service;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.*;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.HistoryDAO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
@Service
public class SessionManager {
    @Autowired
    private HistoryDAO historyDAO;

    // 6.2: Dừng tiến trình
    public void terminateSession(String sessionId) {
        stopInteractionTimer(sessionId);
        // 6.3: Ghi log kết quả
        historyDAO.saveResult(sessionId, "LOSE");
    }

    public void disableInteractionFlags(String sessionId) {
        // Logic đánh dấu session đã bị khóa input
    }

        // Trong SessionManager.java, bạn có thể thêm:
    private Map<String, Boolean> endedSessions = new ConcurrentHashMap<>();

    public boolean isSessionEnded(String sessionId) {
        return endedSessions.getOrDefault(sessionId, false);
    }

    private void stopInteractionTimer(String sessionId) {
        // Logic dừng timer liên quan đến sessionId
        endedSessions.put(sessionId, true); // Đánh dấu session đã kết thúc
    }
}