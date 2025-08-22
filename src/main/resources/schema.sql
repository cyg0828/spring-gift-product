CREATE TABLE IF NOT EXISTS product (
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100)  NOT NULL,
    image_Url VARCHAR(255),
    price     BIGINT        NOT NULL
);