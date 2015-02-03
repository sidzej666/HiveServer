CREATE TABLE players (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL,
	password_hash VARCHAR(50) NOT NULL,
	hash_seed VARCHAR(50) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE games (
	id INT NOT NULL AUTO_INCREMENT,
	player_one_id INT NOT NULL,
	player_two_id INT NULL,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT FOREIGN KEY(player_one_id) REFERENCES players(id),
	CONSTRAINT FOREIGN KEY(player_two_id) REFERENCES players(id)
);