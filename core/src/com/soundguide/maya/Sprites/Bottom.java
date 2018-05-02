package com.soundguide.maya.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Scenes.Hud;
import com.soundguide.maya.Screens.PlayScreen;

/**
 * Created by h on 03.08.2017.
 */

public class Bottom extends InteractiveTileObject {
    public Bottom(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Maia.BOTTOM_BIT);
    }
    @Override
    public void onFeetStep() {
        Gdx.app.log("Bottom", "Collision");
    }
}