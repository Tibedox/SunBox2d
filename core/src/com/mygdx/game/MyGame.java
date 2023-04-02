package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGame extends Game {
	public static final float WIDTH = 12.8f, HEIGHT = 7.2f;

	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	World world;
	Box2DDebugRenderer debugRenderer;

	//Texture img;

	StaticBody wallLeft, wallRight, floor, roof;
	KinematicBody platform;
	ArrayList<DynamicBodyBall> balls = new ArrayList<>();
	DynamicBodyBox box;
	DynamicBodyBall ball0, ball1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		touch = new Vector3();
		world = new World(new Vector2(0,0), true);
		debugRenderer = new Box2DDebugRenderer();

		//img = new Texture("badlogic.jpg");
		floor = new StaticBody(world, WIDTH/2, 0.2f, WIDTH, 0.2f);
		wallLeft = new StaticBody(world, 0.5f, HEIGHT/2, 0.2f, HEIGHT);
		wallRight = new StaticBody(world, WIDTH-0.5f, HEIGHT/2, 0.2f, HEIGHT);
		//roof = new StaticBody(world, WIDTH/2, HEIGHT-0.2f, WIDTH, 0.2f);
		//platform = new KinematicBody(world, WIDTH/2, 2, 2, 1);
		for (int i = 0; i < 1; i++) {
			balls.add(new DynamicBodyBall(world, WIDTH/2+ MathUtils.random(-0.1f, 0.1f), HEIGHT*2+i, 0.4f));
		}
		ball0 = new DynamicBodyBall(world, 1.5f, 2, 0.3f);
		ball1 = new DynamicBodyBall(world, 2.5f, 2, 0.3f);
		box = new DynamicBodyBox(world, 2, 2, 1, 0.2f);
		//jointDistance(ball0.body, box.body, new Vector2(ball0.getX(), ball0.getY()), new Vector2(box.getX()-0.5f, box.getY()));
		//jointDistance(ball1.body, box.body, new Vector2(ball1.getX(), ball1.getY()), new Vector2(box.getX()+0.5f, box.getY()));
		jointFriction(ball0.body, box.body, new Vector2(box.getX(), box.getY()));
		jointFriction(ball1.body, box.body, new Vector2(box.getX(), box.getY()));
	}

	void jointDistance(Body bodyA, Body bodyB, Vector2 v0, Vector2 v1) {
		DistanceJointDef jointDef = new DistanceJointDef();
		jointDef.length = 0;
		jointDef.initialize(bodyA, bodyB, v0, v1);
		DistanceJoint joint = (DistanceJoint) world.createJoint(jointDef);
	}

	void jointFriction(Body bodyA, Body bodyB, Vector2 v) {
		FrictionJointDef jointDef = new FrictionJointDef();
		jointDef.maxForce = 1f;
		jointDef.maxTorque = 10f;
		jointDef.initialize(bodyA, bodyB, v);
		FrictionJoint joint = (FrictionJoint) world.createJoint(jointDef);
	}

	@Override
	public void render () {
		if(Gdx.input.justTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			for (int i = 0; i < balls.size(); i++) {
				balls.get(i).hit(touch.x, touch.y);
			}
			ball0.hit(touch.x, touch.y);
			ball1.hit(touch.x, touch.y);
		}
		//platform.move();
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
