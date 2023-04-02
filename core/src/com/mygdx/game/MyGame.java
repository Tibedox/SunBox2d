package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGame extends Game {
	public static final float WIDTH = 16, HEIGHT = 9;
	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;
	Texture img;
	Wall[] wall = new Wall[3];
	ArrayList<Ball> balls = new ArrayList<>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		img = new Texture("badlogic.jpg");
		wall[0] = new Wall(world, WIDTH/2, 1, WIDTH, 1);
		wall[1] = new Wall(world, 1, 1, 1, HEIGHT);
		wall[2] = new Wall(world, WIDTH-1, 1, 1, HEIGHT);
		for (int i = 0; i < 50; i++) {
			balls.add(new Ball(world, WIDTH/2+ MathUtils.random(-0.1f, 0.1f), HEIGHT*10+i, 0.4f));
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		debugRenderer.render(world, camera.combined);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
