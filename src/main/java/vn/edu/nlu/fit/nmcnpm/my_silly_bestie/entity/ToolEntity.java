package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity;

public class ToolEntity {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String type;         // CARE hoặc PRANK
    private int suspicionRate;   // Tỷ lệ tăng điểm Nghi ngờ (0-100)
    private int happinessRate;   // Tỷ lệ tăng điểm Hạnh phúc (0-100)

    public ToolEntity() {}

    public ToolEntity(String id, String name, String description, String imagePath,
                      String type, int suspicionRate, int happinessRate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.type = type;
        this.suspicionRate = suspicionRate;
        this.happinessRate = happinessRate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getSuspicionRate() { return suspicionRate; }
    public void setSuspicionRate(int suspicionRate) { this.suspicionRate = suspicionRate; }

    public int getHappinessRate() { return happinessRate; }
    public void setHappinessRate(int happinessRate) { this.happinessRate = happinessRate; }

    /** Kiểm tra dụng cụ này thuộc chế độ Chăm sóc */
    public boolean isCare() { return "CARE".equals(type); }

    /** Kiểm tra dụng cụ này thuộc chế độ Trêu chọc */
    public boolean isPrank() { return "PRANK".equals(type); }
}
