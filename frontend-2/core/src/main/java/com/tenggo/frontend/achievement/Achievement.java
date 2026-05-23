package com.tenggo.frontend.achievement;

public class Achievement {
    private final String title;
    private final String description;
    private final boolean unlocked;
    private final String reward;
    private final int coinsRewarded;

    public Achievement(
        String title,
        String description,
        boolean unlocked,
        String reward,
        int coinsRewarded
    ) {
        this.title = title;
        this.description = description;
        this.unlocked = unlocked;
        this.reward = reward;
        this.coinsRewarded = coinsRewarded;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public String getReward() {
        return reward;
    }

    public int getCoinsRewarded() {
        return coinsRewarded;
    }
}

