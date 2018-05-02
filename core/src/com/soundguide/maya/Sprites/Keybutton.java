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

public class Keybutton extends InteractiveTileObject {
    public static boolean keytook = false;
    public Keybutton(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Maia.KEYBUTTON_BIT);
    }
    @Override
    public void onFeetStep() {
        Gdx.app.log("Key", "Collision");
        setCategoryFilter(Maia.DESTROYED_BIT);
        getCella().setTile(null);
        Hud.addScore(10);
        keytook = true;

        Maia.manager.get("audio/sounds/buttonclick.mp3", Sound.class).play();
    }
}
