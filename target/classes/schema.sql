CREATE TABLE IF NOT EXISTS pets (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    image_path VARCHAR(255),
    voice_sfx VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS tools (
    tool_id VARCHAR(50) PRIMARY KEY,
    tool_name VARCHAR(100),
    cursor_path VARCHAR(255), -- Đường dẫn ảnh con trỏ chuột
    sfx_path VARCHAR(255)     -- Âm thanh khi dùng tool
);
DELETE FROM tools;
INSERT INTO tools (tool_id, tool_name, cursor_path, sfx_path) 
VALUES ('comb', 'Cái lược', 'comb.png', 'brushing.mp3');

INSERT INTO tools (tool_id, tool_name, cursor_path, sfx_path) 
VALUES ('hand', 'Bàn tay', 'hand_cursor', 'patting.mp3'); -- 'hand_cursor' dùng làm cờ để bật CSS bàn tay