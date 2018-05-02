package com.soundguide.maya;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soundguide.maya.Screens.GameOverScreen;
import com.soundguide.maya.Screens.PlayScreen;
import com.soundguide.maya.Sprites.Maya;

import static com.soundguide.maya.Sprites.Maya.State.DEAD;
import static com.soundguide.maya.Sprites.Maya.State.WON;
public class Maia extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short MAYA_BIT = 2;
	public static final short STAR_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short OBJECT_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_HEAD_BIT = 64;
	public static final short BOTTOM_BIT = 128;
	public static final short DOOR_BIT = 256;
	public static final short BUTTON_BIT = 512;
	public static final short KEYBUTTON_BIT = 1024;

	public static SpriteBatch batch;
	public static AssetManager manager;

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/level2.mp3", Music.class);
		manager.load("audio/sounds/stargain.mp3", Sound.class);
		manager.load("audio/sounds/greenhit.mp3", Sound.class);
		manager.load("audio/sounds/lose.mp3", Sound.class);
		manager.load("audio/sounds/hittedbyarms.mp3", Sound.class);
		manager.load("audio/sounds/yellowhit.mp3", Sound.class);
		manager.load("audio/sounds/buttonclick.mp3", Sound.class);
		manager.load("audio/sounds/dooropen.mp3", Sound.class);
		manager.load("audio/sounds/win.mp3", Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
