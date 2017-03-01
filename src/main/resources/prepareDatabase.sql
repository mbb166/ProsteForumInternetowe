CREATE TABLE message
(
    idMessage INT(11) NOT NULL,
    title VARCHAR(200) NOT NULL,
    text VARCHAR(2048) NOT NULL,
    idUser INT(11) NOT NULL,
    authorName VARCHAR(30) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE TABLE post
(
    idPost INT(11) NOT NULL,
    text VARCHAR(2048) NOT NULL,
    number INT(2) NOT NULL,
    idTopic INT(11) NOT NULL,
    idUser INT(11) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE TABLE section
(
    idSection INT(11) NOT NULL,
    name VARCHAR(201),
    idParent INT(11)
);
CREATE TABLE topic
(
    idTopic INT(11) NOT NULL,
    title VARCHAR(200) NOT NULL,
    idUser INT(11) NOT NULL,
    idSection INT(11) NOT NULL
);
CREATE TABLE user
(
    idUser INT(10) NOT NULL,
    active TINYINT(1) NOT NULL,
    role VARCHAR(10) NOT NULL,
    password VARCHAR(20) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL
);