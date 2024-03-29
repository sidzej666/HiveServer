DROP TABLE games;
DROP TABLE user_authorities;
DROP TABLE users;

CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	account_expired BOOLEAN NOT NULL DEFAULT FALSE,
	account_locked BOOLEAN NOT NULL DEFAULT FALSE,
	credentials_expired BOOLEAN NOT NULL DEFAULT FALSE,
	account_enabled BOOLEAN NOT NULL DEFAULT TRUE,
	PRIMARY KEY(id)
);

CREATE TABLE user_authorities (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	authority VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE KEY(user_id, authority),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE games (
	id INT NOT NULL AUTO_INCREMENT,
	player_one_id INT NOT NULL,
	player_two_id INT NULL,
	name VARCHAR(50) NOT NULL,
	password VARCHAR(20) NULL,
	PRIMARY KEY(id),
	CONSTRAINT FOREIGN KEY(player_one_id) REFERENCES users(id),
	CONSTRAINT FOREIGN KEY(player_two_id) REFERENCES users(id)
);