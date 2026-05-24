package com.tenggo.frontend.achievement;

public class Achievement {
    private final String title;
    private final String description;
    private boolean unlocked;
    private final String reward;
    private final int coinsRewarded;
    private final String id;

    public Achievement(
        String title,
        String description,
        boolean unlocked,
        String reward,
        int coinsRewarded,
        String id
    ) {
        this.title = title;
        this.description = description;
        this.unlocked = unlocked;
        this.reward = reward;
        this.coinsRewarded = coinsRewarded;
        this.id = id;
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

    public String getID() {
        return id;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}

