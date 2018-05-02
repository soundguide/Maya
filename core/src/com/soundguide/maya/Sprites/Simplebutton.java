package com.soundguide.maya.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Scenes.Hud;
import com.soundguide.maya.Screens.PlayScreen;

/**
 * Created by h on 11.08.2017.
 */

public class Simplebutton extends InteractiveTileObject {
    public Simplebutton(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Maia.BUTTON_BIT);
    }

    @Override
    public void onFeetStep() {
        Gdx.app.log("Button", "Collision");
        setCategoryFilter(Maia.DESTROYED_BIT);
        getCella().setTile(null);
        Hud.addScore(-10);

        Maia.manager.get("audio/sounds/buttonclick.mp3", Sound.class).play();
    }
}
