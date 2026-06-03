package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity;

public class ToolEntity {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String type; // CARE or PRANK
    private int suspicionRate;
    private int happinessRate;

    public ToolEntity() {}

    public ToolEntity(String id, String name, String description, String imagePath, String type, int suspicionRate, int happinessRate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.type = type;
        this.suspicionRate = suspicionRate;
        this.happinessRate = happinessRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSuspicionRate() {
        return suspicionRate;
    }

    public void setSuspicionRate(int suspicionRate) {
        this.suspicionRate = suspicionRate;
    }

    public int getHappinessRate() {
        return happinessRate;
    }

    public void setHappinessRate(int happinessRate) {
        this.happinessRate = happinessRate;
    }
}
