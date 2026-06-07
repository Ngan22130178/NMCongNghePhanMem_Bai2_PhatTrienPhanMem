package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.dto;

public record ToolDTO(
    String id,
    String name,
    String description,
    String imagePath,
    String type,
    int suspicionRate,
    int happinessRate
) {}
