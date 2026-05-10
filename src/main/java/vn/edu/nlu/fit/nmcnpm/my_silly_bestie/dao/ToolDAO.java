package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

@Repository
public class ToolDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ToolEntity findById(String id) {
        String sql = "SELECT tool_id as toolId, tool_name as toolName, " +
                     "cursor_path as cursorPath, sfx_path as sfxPath " +
                     "FROM tools WHERE tool_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, 
                new BeanPropertyRowMapper<>(ToolEntity.class), id);
        } catch (Exception e) {
            return null;
        }
    }
}
