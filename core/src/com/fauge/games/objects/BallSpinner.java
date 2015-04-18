package com.fauge.games.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fauge.games.RotateState;
import com.fauge.games.screen.GameScreen;

public class BallSpinner implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Body A = contact.getFixtureA().getBody();
		Body B = contact.getFixtureB().getBody();
		if(A.getType() == BodyType.KinematicBody && B.getType() == BodyType.DynamicBody){
//			body B is the ball and A is the paddle
			float center = A.getPosition().y + .5f * Paddle.HEIGHT;
			float difference = center - B.getPosition().y;
			float percent = difference / (6) - .5f;
			float degree = percent*75;
			Ball.speedMult+=.1f;
			float distance = 5 * Ball.speedMult * MathUtils.cosDeg(degree);
			float wide = 5  * Ball.speedMult * MathUtils.sinDeg(degree);
			Ball.setLinearvelocity(new Vector2(distance, wide));
			//B.setLinearVelocity(distance * 5, wide);
			float rotateDegrees = (float) Math.random() * 4f;
			rotateDegrees = (int) rotateDegrees;
			if(rotateDegrees == 1){
//					rotate 90
				GameScreen.rotateProgress = 0;
				GameScreen.rotateState = RotateState.ROTATE90;
			}
			else if(rotateDegrees == 2){
//					rotate 180
				GameScreen.rotateProgress = 0;
				if(MathUtils.randomBoolean())	
					GameScreen.rotateState = RotateState.ROTATE180;
				else
					GameScreen.rotateState = RotateState.ROTATE_180;
			}
			else if(rotateDegrees == 3){
//					rotate -90
				GameScreen.rotateProgress = 0;
				GameScreen.rotateState = RotateState.ROTATE_90;
			}
			else{
//					dont rotate
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}