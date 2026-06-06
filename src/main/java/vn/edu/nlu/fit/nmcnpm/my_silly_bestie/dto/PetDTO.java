package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto;

public record PetDTO(
    String id,
    String name,
    String description,
    String imagePath,
    String voiceSfx,
    int difficulty
) {}
