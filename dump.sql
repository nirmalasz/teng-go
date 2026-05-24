CREATE TABLE players (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    high_score INT DEFAULT 0,
    total_coins INT DEFAULT 0,
    highest_level_reached INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scores (
    id_score UUID PRIMARY KEY,
    player_id UUID NOT NULL,
    score_value INT NOT NULL DEFAULT 0,
    coins_collected INT NOT NULL DEFAULT 0,
    stage_reached INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_player
        FOREIGN KEY (player_id) 
        REFERENCES players (id)
        ON DELETE CASCADE
);


CREATE TABLE achievements (
    achievement_id UUID PRIMARY KEY,
    achievement_name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    reward_coins INTEGER DEFAULT 0
);

-- 2. Player Achievements Table
CREATE TABLE player_achievements (
    player_achievement_id UUID PRIMARY KEY,
    player_id UUID NOT NULL,
    achievement_id UUID NOT NULL,
    unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_player 
        FOREIGN KEY (player_id) 
        REFERENCES players(id) 
        ON DELETE CASCADE,

    CONSTRAINT fk_achievement 
        FOREIGN KEY (achievement_id) 
        REFERENCES achievements(achievement_id)
        ON DELETE CASCADE
);