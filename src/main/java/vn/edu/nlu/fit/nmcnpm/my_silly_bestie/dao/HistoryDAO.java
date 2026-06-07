package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public class HistoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 6.3: Ghi log kết quả "LOSE" vào DB
    public void saveResult(String sessionId, String result) {
        String sql = "INSERT INTO game_history (session_id, result, created_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, sessionId, result, LocalDateTime.now());
    }
}