DELETE FROM pets;
-- Chỉ lưu tên file, không lưu đường dẫn dài dòng của IDE
INSERT INTO pets (id, name, image_path, voice_sfx) 
VALUES ('capy-01', 'Capybara', 'capy.png', 'capy_sound.mp3');

INSERT INTO pets (id, name, image_path, voice_sfx) 
VALUES ('cat-02', 'Mèo Ngáo', 'cat.png', 'cat_meow.mp3');