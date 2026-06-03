package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity;

public class PetEntity {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String voiceSfx;
    private int difficulty;

    public PetEntity() {}

    public PetEntity(String id, String name, String description, String imagePath, String voiceSfx, int difficulty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.voiceSfx = voiceSfx;
        this.difficulty = difficulty;
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

    public String getVoiceSfx() {
        return voiceSfx;
    }

    public void setVoiceSfx(String voiceSfx) {
        this.voiceSfx = voiceSfx;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
