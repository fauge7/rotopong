package com.fauge.games.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall {

	public static final float WIDTH = 72;
	public static final float HEIGHT = 2;
	public float posX, posY;
	public Body body;
	PolygonShape shape;
	public Wall(Vector2 startPos) {
		// TODO Auto-generated constructor stub
		this.posX = startPos.x;
		this.posY = startPos.y;
	}
	public void Render(){
		//render userdata for paddles
	}
	public void initBox2D(World world, BodyDef bodyDef){
		bodyDef.type = BodyType.StaticBody;
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
