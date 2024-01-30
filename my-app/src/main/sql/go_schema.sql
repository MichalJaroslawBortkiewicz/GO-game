DROP SCHEMA IF EXISTS go_games;
CREATE SCHEMA go_games;

USE go_games;



CREATE TABLE IF NOT EXISTS games (
	game_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	points_difference TINYINT UNSIGNED NOT NULL,
	black_won BOOLEAN,
	
	
	PRIMARY KEY (game_id)
);


CREATE TABLE IF NOT EXISTS game_moves (
	game_id INT UNSIGNED NOT NULL,
	move_nr TINYINT UNSIGNED NOT NULL,
	move VARCHAR(5),

	
	CONSTRAINT fk_game_moves_games FOREIGN KEY (game_id)
	REFERENCES games (game_id) ON DELETE CASCADE ON UPDATE CASCADE
);



DELIMITER $$

CREATE PROCEDURE IF NOT EXISTS new_game(OUT amogus INT)
BEGIN    
	INSERT INTO games (points_difference, black_won) VALUES (0, NULL);
	SELECT LAST_INSERT_ID() INTO amogus;
END $$

DELIMITER ;