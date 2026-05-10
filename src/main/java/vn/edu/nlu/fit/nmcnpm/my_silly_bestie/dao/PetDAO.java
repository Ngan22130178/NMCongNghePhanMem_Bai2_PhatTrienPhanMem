package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;

@Repository
public class PetDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PetEntity findPetById(String id) {
        String sql = "SELECT id, name, image_path, voice_sfx FROM pets WHERE id = ?";
        try {
            // BeanPropertyRowMapper sẽ tự đổi image_path thành imagePath
            return jdbcTemplate.queryForObject(sql, 
                new BeanPropertyRowMapper<>(PetEntity.class), id);
        } catch (Exception e) {
            return null; // Không tìm thấy pet
        }
    }
}