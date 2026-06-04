package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;

import java.util.List;

/**
 * Tầng Truy cập Dữ liệu cho bảng pets.
 * Sử dụng JdbcTemplate để truy vấn trực tiếp bằng SQL thuần.
 */
@Repository
public class PetDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** Lấy toàn bộ danh sách thú cưng để hiển thị trang chủ */
    public List<PetEntity> findAll() {
        String sql = "SELECT id, name, description, image_path, voice_sfx, difficulty FROM pets ORDER BY difficulty";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PetEntity.class));
    }

    /** Tìm một thú cưng theo ID */
    public PetEntity findById(String id) {
        String sql = "SELECT id, name, description, image_path, voice_sfx, difficulty FROM pets WHERE id = ?";
        List<PetEntity> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PetEntity.class), id);
        return results.isEmpty() ? null : results.get(0);
    }
}
