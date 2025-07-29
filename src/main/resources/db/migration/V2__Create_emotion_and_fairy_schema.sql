CREATE TABLE emotions
(
    id          BIGINT PRIMARY KEY                  NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)                        NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_id  VARCHAR(255)                        NOT NULL,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    updated_id  VARCHAR(255)                        NOT NULL,
    deleted_at  TIMESTAMP NULL
);

CREATE TABLE fairies
(
    id                   BIGINT PRIMARY KEY,
    name                 VARCHAR(255)                        NOT NULL UNIQUE,
    image_url            VARCHAR(255),
    silhouette_image_url VARCHAR(255),
    emotion_id           BIGINT,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_id           VARCHAR(255)                        NOT NULL,
    updated_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    updated_id           VARCHAR(255)                        NOT NULL,
    deleted_at           TIMESTAMP NULL,
    FOREIGN KEY (emotion_id) REFERENCES emotions (id)
);