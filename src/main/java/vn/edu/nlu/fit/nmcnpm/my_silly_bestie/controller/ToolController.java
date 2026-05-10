package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.ToolDAO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.ToolDTO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.ToolEntity;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    @Autowired
    private ToolDAO toolDAO;

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> getTool(@PathVariable String id) {
        ToolEntity tool = toolDAO.findById(id);
        
        if (tool == null) {
            return ResponseEntity.notFound().build();
        }

        ToolDTO dto = new ToolDTO(
            tool.getToolId(), 
            tool.getToolName(), 
            tool.getCursorPath(), 
            tool.getSfxPath()
        );
        
        return ResponseEntity.ok(dto);
    }
}