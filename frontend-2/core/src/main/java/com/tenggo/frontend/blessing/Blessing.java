package com.tenggo.frontend.blessing;

import com.tenggo.frontend.entities.Player;

public interface Blessing {
    String getName();
    String getDescription();
    void apply(Player player);
}
