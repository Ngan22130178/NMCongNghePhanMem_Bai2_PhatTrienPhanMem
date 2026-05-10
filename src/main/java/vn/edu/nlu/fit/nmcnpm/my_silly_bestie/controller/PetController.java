package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dao.PetDAO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto.PetDTO;
import vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity.PetEntity;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private PetDAO petDAO;

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable String id) {
        PetEntity pet = petDAO.findPetById(id);
        if (pet == null) return ResponseEntity.notFound().build();

        // Chuyển đổi Entity sang DTO (Java 21 Record)
        PetDTO dto = new PetDTO(pet.getName(), pet.getImagePath(), pet.getVoiceSfx());
        return ResponseEntity.ok(dto);
    }
}
