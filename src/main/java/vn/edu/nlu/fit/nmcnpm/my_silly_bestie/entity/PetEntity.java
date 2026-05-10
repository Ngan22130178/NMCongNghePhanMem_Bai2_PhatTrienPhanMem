package vn.edu.nlu.fit.nmcnpm.my_silly_bestie.entity;

public class PetEntity {
    private String id;
    private String name;
    private String imagePath; // Tương ứng cột image_path trong SQL
    private String voiceSfx;  // Tương ứng cột voice_sfx trong SQL

    // ĐẢM BẢO CÓ ĐỦ GETTER/SETTER CHO TẤT CẢ CÁC BIẾN
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getVoiceSfx() { return voiceSfx; }
    public void setVoiceSfx(String voiceSfx) { this.voiceSfx = voiceSfx; }
}