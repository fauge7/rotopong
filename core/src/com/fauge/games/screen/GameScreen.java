package com.fauge.games.screen;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fauge.games.RotateState;
import com.fauge.games.objects.Ball;
import com.fauge.games.objects.BallSpinner;
import com.fauge.games.objects.Paddle;
import com.fauge.games.objects.Wall;

public class GameScreen implements Screen{
	World world;
	Box2DDebugRenderer debugRenderer;
	Game game;
	public static OrthographicCamera cam;
	SpriteBatch batch;
	Viewport view;
	
	public static int player_Score = 0;
	public static int computer_Score = 0;
	Paddle player;
	Paddle computer;
	Ball ball;
	Wall top;
	Wall bottom;
	RayHandler rayHandler;
	PointLight light;
	BallSpinner ballSpinner;
	public static boolean pause;
	private float camera_angle;
	//spinning stuff
	public static float rotateProgress;
	public static float previousRotate;
	public static RotateState rotateState;
	
	public GameScreen(Game game,SpriteBatch batch) {
	// TODO Auto-generated constructor stub
		this.game = game;
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera_angle = 0;
		view = new StretchViewport(72, 48,cam);
		cam.position.set(72/2f,48/2f,0);
		cam.update();
		this.batch = batch;
		//box2d stuff
		world = new World(new Vector2(0,0), true);
		ballSpinner = new BallSpinner();
		world.setContactListener(ballSpinner);
		debugRenderer = new Box2DDebugRenderer();
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		//making paddles
		player = new Paddle(false, new Vector2(5, view.getWorldHeight()/2));
		player.initBox2D(world, bodyDef);
		computer = new Paddle(true, new Vector2(view.getWorldWidth()-5, view.getWorldHeight()/2));
		computer.initBox2D(world, bodyDef);
		top = new Wall(new Vector2(view.getWorldWidth()/2, 0));
		top.initBox2D(world, bodyDef);
		bottom = new Wall(new Vector2(view.getWorldWidth()/2, view.getWorldHeight()));
		bottom.initBox2D(world, bodyDef);
		ball = new Ball(new Vector2(view.getWorldWidth()/2,view.getWorldHeight()/2), new Vector2(-5,0));
		ball.initBox2D(world, bodyDef, fixtureDef);
		
		//box2d lights
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(.15f);
		rayHandler.setBlurNum(2);
		light = new PointLight(rayHandler, 1024);
		light.setPosition(Ball.pos);
		light.setColor(Color.CYAN);
		light.setDistance(50);
		light.attachToBody(ball.getBody());
		
		pause = false;
		//rotate stuff
		rotateProgress = 0;
		rotateState = RotateState.NOT_ROTATED;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.setProjectionMatrix(cam.combined);
		if(pause){
			rayHandler.setCombinedMatrix(cam);
			debugRenderer.render(world, cam.combined);
			rayHandler.updateAndRender();
		}
		else{
			Rotate();
			rayHandler.setCombinedMatrix(cam);
			player.update();
			computer.update();
			ball.update();
			world.step(1/30f, 10,5);
			debugRenderer.render(world, cam.combined);
			rayHandler.updateAndRender();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		view.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		rayHandler.dispose();
		player.Dispose();
		computer.Dispose();
		ball.Dispose();
		world.dispose();
		
	}
	public void Rotate(){
		if(rotateState == RotateState.ROTATE90){
			rotateProgress++;
			cam.rotate(1);
			camera_angle+=1;
			if(camera_angle > 270){
				cam.zoom-=.01f;
			}
			else if(camera_angle > 180){
				cam.zoom+=.01f;
			}
			else if(camera_angle > 90){
				cam.zoom-=.01f;
			}
			else if(camera_angle > 0){
				cam.zoom+=.01f;
			}
		}
		else if(rotateState == RotateState.ROTATE_90){
			rotateProgress--;
			cam.rotate(-1);
			camera_angle+=-1;
			float absoluteCameraAngle = Math.abs(camera_angle);
			if(absoluteCameraAngle > 270){
				cam.zoom+=.01f;
			}
			else if(absoluteCameraAngle > 180){
				cam.zoom-=.01f;
			}
			else if(absoluteCameraAngle > 90){
				cam.zoom+=.01f;	
			}
			else if(absoluteCameraAngle > 0){
				cam.zoom-=.01f;
			}
		}
		else if(rotateState == RotateState.ROTATE180){
			rotateProgress+=2;
			cam.rotate(2);
			camera_angle+=2;
			if(camera_angle > 270){
				cam.zoom-=.01f;
			}
			else if(camera_angle > 180){
				cam.zoom+=.01f;
			}
			else if(camera_angle > 90){
				cam.zoom-=.01f;	
			}
			else if(camera_angle > 0){
				cam.zoom+=.01f;
			}
		}
		else if(rotateState == RotateState.ROTATE_180){
			rotateProgress-=2;
			cam.rotate(-2);
			camera_angle+=-2;
			float absoluteCameraAngle = Math.abs(camera_angle);
			if(absoluteCameraAngle > 270){
				cam.zoom+=.01f;
			}
			else if(absoluteCameraAngle > 180){
				cam.zoom-=.01f;
			}
			else if(absoluteCameraAngle > 90){
				cam.zoom+=.01f;	
			}
			else if(absoluteCameraAngle > 0){
				cam.zoom-=.01f;
			}
		}	
		if(camera_angle > 360){
			camera_angle = 0;
		}
		else if(camera_angle < 0){
			camera_angle = 360;
		}
		cam.update();
		switch (rotateState) {
		case ROTATE90:
			if(rotateProgress == 90)
				rotateState = RotateState.NOT_ROTATED;
			break;
		case ROTATE180:
			if(rotateProgress == 180)
				rotateState = RotateState.NOT_ROTATED;
			break;
		case ROTATE_90:
			if(rotateProgress == -90)
				rotateState = RotateState.NOT_ROTATED;
			break;
		case ROTATE_180:
			if(rotateProgress == -180)
				rotateState = RotateState.NOT_ROTATED;
			break;
		default:
			break;
		}
	}
	public void newPoint(boolean playerScored){
		if(playerScored)
			GameScreen.player_Score++;
		else
			GameScreen.computer_Score++;
	}
}