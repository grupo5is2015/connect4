DROP TABLE IF EXISTS users;
CREATE TABLE users(
    id INT(11) NOT NULL AUTO_INCREMENT,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
    password  varchar(100),
    nickname  varchar(20),
  CONSTRAINT users_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS rankings;
CREATE TABLE rankings(
    id INT(11) NOT NULL AUTO_INCREMENT,
    rank INT(10),
    won INT(10),
    tie INT(10),
    lost INT(10),
    user_id INT(11) UNIQUE,
  CONSTRAINT ranks_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS games;
CREATE TABLE games (
    id INT(11) NOT NULL AUTO_INCREMENT,
    finished bool,
    draw bool,
    user_id INT(11), /* el ganador si existiese */
 
  CONSTRAINT games_pk PRIMARY KEY(id)
);


DROP TABLE IF EXISTS games_users;
CREATE TABLE games_users (
    id INT(11) NOT NULL AUTO_INCREMENT,
    game_id INT(11),
    user_id INT(11),
  CONSTRAINT games_users_pk PRIMARY KEY(id)
);

DROP TABLE IF EXISTS boards;
CREATE TABLE boards (
    id INT(11) NOT NULL AUTO_INCREMENT,
    game_id INT(11),
    user_id INT(11), /* player que inicia*/
  CONSTRAINT boards_pk PRIMARY KEY(id)
);

DROP TABLE IF EXISTS moves;
CREATE TABLE moves (
    id INT(11) NOT NULL AUTO_INCREMENT,
    board_id INT(11),
    user_id INT(11), /* player que ejecuta*/
  CONSTRAINT moves_pk PRIMARY KEY(id)
);



