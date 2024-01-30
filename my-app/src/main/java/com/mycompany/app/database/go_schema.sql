CREATE SCHEMA IF NOT EXISTS go_games;

USE go_games;


CREATE TABLE IF NOT EXISTS players (
	player_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	
	PRIMARY KEY (player_id)
);


CREATE TABLE IF NOT EXISTS games (
	game_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	blackp_id INT UNSIGNED,
	whitep_id INT UNSIGNED,
	points_difference TINYINT UNSIGNED NOT NULL,
	black_won BOOLEAN,
	
	
	PRIMARY KEY (game_id),
	CONSTRAINT fk_games_players_white FOREIGN KEY (blackp_id)
	REFERENCES players (player_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT fk_games_players_black FOREIGN KEY (whitep_id)
	REFERENCES players (player_id) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS game_moves (
	game_id INT UNSIGNED NOT NULL,
	move_nr TINYINT UNSIGNED NOT NULL,
	black_move VARCHAR(5),
	white_move VARCHAR(5),
	
	CONSTRAINT fk_game_moves_games FOREIGN KEY (game_id)
	REFERENCES games (game_id) ON DELETE CASCADE ON UPDATE CASCADE
);