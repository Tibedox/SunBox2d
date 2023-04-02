package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGame extends Game {
	public static final float WIDTH = 12.8f, HEIGHT = 7.2f;

	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;

	//Texture img;

	StaticBody wallLeft, wallRight, floor;
	ArrayList<DynamicBody> balls = new ArrayList<>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		//img = new Texture("badlogic.jpg");
		floor = new StaticBody(world, WIDTH/2, 0.2f, WIDTH, 0.2f);
		wallLeft = new StaticBody(world, 1, HEIGHT/2, 0.2f, HEIGHT);
		wallRight = new StaticBody(world, WIDTH-1, HEIGHT/2, 0.2f, HEIGHT);
		for (int i = 0; i < 50; i++) {
			balls.add(new DynamicBody(world, WIDTH/2+ MathUtils.random(-0.1f, 0.1f), HEIGHT*10+i, 0.4f));
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		debugRenderer.render(world, camera.combined);
		/*camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();*/
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
