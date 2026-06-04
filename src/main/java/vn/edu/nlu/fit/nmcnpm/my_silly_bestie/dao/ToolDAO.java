package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

import java.util.List;

/**
 * Tầng Truy cập Dữ liệu cho bảng tools.
 * Sử dụng JdbcTemplate để truy vấn trực tiếp bằng SQL thuần.
 */
@Repository
public class ToolDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** Lấy toàn bộ danh sách dụng cụ để hiển thị trên khay dụng cụ */
    public List<ToolEntity> findAll() {
        String sql = "SELECT id, name, description, image_path, type, suspicion_rate, happiness_rate FROM tools ORDER BY type";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ToolEntity.class));
    }

    /** Tìm một dụng cụ theo ID khi người chơi click sử dụng */
    public ToolEntity findById(String id) {
        String sql = "SELECT id, name, description, image_path, type, suspicion_rate, happiness_rate FROM tools WHERE id = ?";
        List<ToolEntity> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ToolEntity.class), id);
        return results.isEmpty() ? null : results.get(0);
    }
}
