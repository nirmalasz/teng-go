package com.tenggo.frontend.blessing;

import com.badlogic.gdx.utils.Array;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class BlessingManager {
    private final Array<Blessing> allBlessings;

    public BlessingManager() {
        allBlessings = new Array<>();
        allBlessings.add(new HealthBlessing());
        allBlessings.add(new DamageBlessing());
        allBlessings.add(new SpeedBlessing());
    }

    public List<Blessing> getRandomBlessings() {
        List<Blessing> list =
            new ArrayList<>();

        for (int i = 0; i < allBlessings.size; i++) {
            list.add(allBlessings.get(i));
        }

        Collections.shuffle(list);
        return list.subList(0, 3);
    }
}
