CREATE TABLE camera
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    nasa_id    BIGINT       NOT NULL,
    name       VARCHAR(256) NOT NULL,
    created_at BIGINT       NOT NULL,

    PRIMARY KEY (id),
    UNIQUE nasa_id_uc (nasa_id)
);

CREATE TABLE picture
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    nasa_id    BIGINT NOT NULL,
    created_at BIGINT NOT NULL,
    img_src    TEXT   NOT NULL,
    camera_id  BIGINT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT camera_fk FOREIGN KEY (camera_id) REFERENCES camera (id),
    CONSTRAINT nasa_id_uc UNIQUE (nasa_id)
);