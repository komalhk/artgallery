CREATE TABLE IF NOT EXISTS artist (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    genre VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS art (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    theme VARCHAR(255),
    artistId INT,
    FOREIGN KEY(artistId) REFERENCES artist(id)
);


CREATE TABLE IF NOT EXISTS gallery (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    location VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS artist_gallery (
    artistId INT,
    galleryId INT,
    PRIMARY KEY(artistId, galleryId),
    FOREIGN KEY(artistId) REFERENCES artist(id),
    FOREIGN KEY(galleryId) REFERENCES gallery(id)
);