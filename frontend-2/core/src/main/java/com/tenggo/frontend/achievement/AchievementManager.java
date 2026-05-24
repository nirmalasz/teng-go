package com.tenggo.frontend.achievement;

import com.badlogic.gdx.Gdx;
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
            10,
            "d1dc4ed0-ef18-4a74-9a80-701d7e330225"
            )
        );

        achievements.add(
            new Achievement(
                "Temporary Escape",
                "Reach Stage 3",
                GameManager.getInstance().getHighestLevelReached() >= 3,
                "(50 Coins)",
                50,
                "2b4b8ca7-404d-4c87-b3e2-f9640405476b"
            )
        );

        achievements.add(
            new Achievement(
                "Karl Marx Chosen",
                "Escape Stage 5",
                GameManager.getInstance().getHighestLevelReached() >= 5,
                "(100 Coins)",
                10,
                "1344ff81-778a-4268-9c7d-397fc68ab7c4"
            )
        );

        achievements.add(
            new Achievement(
                "24 Karats Labewbew",
                "Collect 100 Coins",
                GameManager.getInstance().getCoinsCollected() >= 100,
                "(24 Coins)",
                24,
                "1d3fac37-7fd4-4baa-96b8-bae93111566a"
            )
        );

        achievements.add(
            new Achievement(
                "Gold, Glory, Gospel",
                "Escape, Collect 1000 Coins, Slay Total of 100 Enemies",
                GameManager.getInstance().getCoinsCollected() >= 1000
                    && GameManager.getInstance().getScore() >= 10000
                    && GameManager.getInstance().getHighestLevelReached() >= 5,
                "(1000 Coins)",
                1000,
                "723d9849-41c0-43ce-a785-26f7354e48cb"
            )
        );

        achievements.add(
            new Achievement(
                "Ruthless Killer",
                "Kill 10 Enemies",
                GameManager.getInstance().getScore() >= 1000,
                "(500 Coins)",
                500,
                "ad34efe4-9231-4018-96b9-5031ec116a44"
            )
        );

        achievements.add(
            new Achievement(
                "Second Chance",
                "Unlock Death Defiance",
                GameManager.getInstance().getDeathDefianceCount() > 0,
                "(10 Coins)",
                10,
                "c6ed33fe-fd01-4f33-8a0a-4f84feb56822"
            )
        );

        achievements.add(
            new Achievement(
                "Staff of Hermes",
                "Upgrade Dash 1 times",
                GameManager.getInstance().getDashCount() >= 2,
                "(10 Coins)",
                10,
                "6912142c-9540-4b3e-b10f-372e47504b1f"
            )
        );

        return achievements;
    }
    public static void checkAndSyncAchievement(){
        GameManager.getInstance().fetchUnlockedAchievements(
            new GameManager.UnlockedFetchCallback() {
                @Override
                public void onSuccess(Array<String> unlockedAchievementID) {
                    Array<Achievement> localStatus = getAchievements();
                    for(Achievement achievement: localStatus){
                        if (achievement.isUnlocked() && !unlockedAchievementID.contains(achievement.getID(), false)) {
                            Gdx.app.log("ACHIEVEMENT SYNC",
                                "New achievement detected! Syncing: " + achievement.getTitle());
                                GameManager.getInstance().unlockAchievement(achievement.getID());
                            achievement.setUnlocked(true);
                            GameManager.getInstance().addCoins(achievement.getCoinsRewarded());
                        }
                    }
                }

                @Override
                public void onError(String error) {
                    Gdx.app.error("ACHIEVEMENT_SYNC", "Failed to fetch server state. Aborting sync: " + error);
                }
            });
    }
}
