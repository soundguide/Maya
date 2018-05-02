package com.soundguide.maya.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Screens.PlayScreen;
import com.soundguide.maya.Sprites.Bottom;
import com.soundguide.maya.Sprites.Doors;
import com.soundguide.maya.Sprites.Enemy;
import com.soundguide.maya.Sprites.GreenMonster;
import com.soundguide.maya.Sprites.Keybutton;
import com.soundguide.maya.Sprites.Simplebutton;
import com.soundguide.maya.Sprites.Stars;
import com.soundguide.maya.Sprites.YellowMonster;

/**
 * Created by h on 18.04.2017.
 */

public class B2WorldCreator {
    private Array<GreenMonster> greenmonsters;
    private Array<YellowMonster> yellownmonsters;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
//ground:
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Maia.PPM, (rect.getY() + rect.getHeight() / 2) / Maia.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Maia.PPM, rect.getHeight() / 2 / Maia.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Maia.OBJECT_BIT;
            body.createFixture(fdef);
        }
//stars:
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Stars(screen, rect);
        }
        greenmonsters = new Array<GreenMonster>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            greenmonsters.add(new GreenMonster(screen, rect.getX() / Maia.PPM, rect.getY() / Maia.PPM));
        }
        yellownmonsters = new Array<YellowMonster>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
           yellownmonsters.add(new YellowMonster(screen, rect.getX() / Maia.PPM, rect.getY() / Maia.PPM));
        }
        //bottom:
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Bottom(screen, rect);
        }
        //door:
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Doors(screen, rect);
        }
        //buttons
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Simplebutton(screen, rect);
        }
        //keys
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Keybutton(screen, rect);
        }
    }


    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(greenmonsters);
        enemies.addAll(yellownmonsters);
        return enemies;
    }
}
