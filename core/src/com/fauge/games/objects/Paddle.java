package com.fauge.games.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fauge.games.screen.GameScreen;

public class Paddle {

	public static final float WIDTH = 1.5f;
	public static final float HEIGHT = 6.0f;
	public static final float MAX_VEL = 5f;
	
	public float posX, posY;
	public Body body;
	private boolean computer_Controlled;
	PolygonShape shape;
	public Paddle(boolean computer_Controlled, Vector2 startPos) {
		// TODO Auto-generated constructor stub
		this.computer_Controlled = computer_Controlled;
		this.posX = startPos.x;
		this.posY = startPos.y;
	}
	public void Render(){
		//render userdata for paddles
	}
	public void update(){
		//ai
		if(computer_Controlled){
			if(body.getPosition().y > Ball.getPos().y){
				body.setLinearVelocity(0, Math.max(-MAX_VEL, Ball.getLinearVelocity().y));
			}
			else{
				body.setLinearVelocity(0, Math.max(MAX_VEL, Ball.getLinearVelocity().y));
			}
		}
		//player controlled
		else{
			if(Gdx.input.isKeyPressed(Keys.UP)){
				body.setLinearVelocity(0, 5);	
			}
			else if(Gdx.input.isKeyPressed(Keys.DOWN)){
				body.setLinearVelocity(0, -5);
			}
			else{
				body.setLinearVelocity(0, 0);
			}
		}
		if(body.getPosition().y < -3f){
			if(body.getLinearVelocity().y < 0)
				body.setLinearVelocity(0, 0);
		}
		else if(body.getPosition().y > GameScreen.cam.viewportHeight){
			if(body.getLinearVelocity().y > 0)
				body.setLinearVelocity(0, 0);
		}
	}
	public void initBox2D(World world, BodyDef bodyDef){
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(posX, posY);
		body = world.createBody(bodyDef);
		shape = new PolygonShape();
		shape.setAsBox(WIDTH, HEIGHT);
		body.createFixture(shape, 1);
	}
	public void Dispose(){
		shape.dispose();
	}
}