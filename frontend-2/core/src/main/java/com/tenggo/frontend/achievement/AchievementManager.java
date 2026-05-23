package com.tenggo.frontend.achievement;

import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.core.GameManager;

public class AchievementManager {

    public static Array<Achievement> getAchievements() {
        Array<Achievement> achievements = new Array<>();

        achievements.add(new Achievement(
                "My First Day",
                "Reach Stage 1",
            GameManager.getInstance().getHighestLevelReached() >= 1,
            "(10 Coins)",
            10
            )
        );

        achievements.add(
            new Achievement(
                "Temporary Escape",
                "Reach Stage 3",
                GameManager.getInstance().getHighestLevelReached() >= 3,
                "(50 Coins)",
                50
            )
        );

        achievements.add(
            new Achievement(
                "Karl Marx Chosen",
                "Reach Stage 5",
                GameManager.getInstance().getHighestLevelReached() >= 5,
                "(100 Coins)",
                10
            )
        );

        achievements.add(
            new Achievement(
                "24 karats Labewbew",
                "Collect 100 Coins",
                GameManager.getInstance().getCoinsCollected() >= 100,
                "(24 Coins)",
                24
            )
        );

        achievements.add(
            new Achievement(
                "Gold, Glory, Gospel",
                "Escape, Collect 1000 Coins, Slay Total of 100 Enemies",
                GameManager.getInstance().getCoinsCollected() >= 1000
                    && GameManager.getInstance().getScore() >= 1000
                    && GameManager.getInstance().getHighestLevelReached() >= 5,
                "(1000 Coins)",
                1000
            )
        );

        achievements.add(
            new Achievement(
                "Ruthless Killer",
                "Kill 10 Enemies",
                GameManager.getInstance().getScore() >= 100,
                "(500 Coins)",
                500
            )
        );

        achievements.add(
            new Achievement(
                "Second Chance",
                "Unlock Death Defiance",
                GameManager.getInstance().getDeathDefianceCount() > 0,
                "(10 Coins)",
                10
            )
        );

        achievements.add(
            new Achievement(
                "Hermes' Staff",
                "Upgrade Dash 1 times",
                GameManager.getInstance().getDashCount() >= 2,
                "(10 Coins)",
                10
            )
        );

        return achievements;
    }
}
