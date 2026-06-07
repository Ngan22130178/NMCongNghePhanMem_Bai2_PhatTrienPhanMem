package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.PetDAO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.PenaltyResponseDTO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.PetDTO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.PrankRequest; // Import DTO mới
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.service.PenaltyService; // Import Service

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private PenaltyService penaltyService; // Phải có dòng này mới gọi được processInteraction

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable String id) {
        PetEntity pet = petDAO.findPetById(id);
        if (pet == null) return ResponseEntity.notFound().build();

        PetDTO dto = new PetDTO(pet.getName(), pet.getImagePath(), pet.getVoiceSfx());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/interact/prank")
    public ResponseEntity<?> prankPet(@RequestBody PrankRequest request) {
        // 5.1.2: Luồng phụ - Chặn spam request
        if (sessionManager.isSessionEnded(request.sessionId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Phiên chơi đã kết thúc!");
        }
        return ResponseEntity.ok(penaltyService.processInteraction(request));
    }
}