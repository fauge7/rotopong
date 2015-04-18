package com.fauge.games.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.fauge.games.screen.GameScreen;

public class Ball {
	
	public static final float RADIUS = 2;
	public static float speedMult;
	private static Body body;
	private Fixture fixture;
	CircleShape circle;
	private Vector2 startingPos;
	private Vector2 startingVel;
	static public Vector2 pos;
	static public Vector2 vel;
	public static float Omega;
	public Ball(Vector2 startPos, Vector2 velocity) {
		// TODO Auto-generated constructor stub
		startingPos = startPos;
		Ball.pos = startingPos;
		startingVel = velocity;
		Ball.vel = startingVel;
		circle = new CircleShape();
		circle.setRadius(RADIUS);
		Omega = 0;
		Ball.speedMult = 1;
	}
	public void update(){
		if(body.getPosition().x < RADIUS){
			reset();
		}
		else if(pos.y > GameScreen.cam.viewportWidth-RADIUS){
			reset();
		}
	}
	public void render(){
		
	}
	public void initBox2D(World world, BodyDef bodyDef, FixtureDef fixtureDef){
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		body = world.createBody(bodyDef);
		fixtureDef.shape = circle;
		fixtureDef.restitution = 1f;
		fixtureDef.friction = 0f;
		fixture = body.createFixture(fixtureDef);
		body.setLinearVelocity(vel);
		body.setAngularVelocity(Omega);
	}
	public static Vector2 getLinearVelocity(){
		return body.getLinearVelocity();
	}
	public static void setLinearvelocity(Vector2 velocity){
		body.setLinearVelocity(velocity);
	}
	public void Dispose(){
		circle.dispose();
	}
	public Body getBody(){
		return body;
	}
	public Fixture getfixture(){
		return fixture;
	}
	public void reset(){
		
		Ball.pos = startingPos;
		Ball.setLinearvelocity(startingVel);
		GameScreen.pause = true;
	}
	public static Vector2 getPos(){
		return body.getPosition();
	}
}