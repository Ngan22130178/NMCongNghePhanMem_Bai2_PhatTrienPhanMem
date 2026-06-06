DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS tools;

CREATE TABLE pets (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    image_path VARCHAR(255),
    voice_sfx VARCHAR(255),
    difficulty INT NOT NULL
);

CREATE TABLE tools (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    image_path VARCHAR(255),
    type VARCHAR(20) NOT NULL,
    suspicion_rate INT NOT NULL,
    happiness_rate INT NOT NULL
);
