package com.fauge.games;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fauge.games.screen.GameScreen;

public class RotoPongMain extends Game implements ApplicationListener{
	SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this, batch));
	}

	@Override
	public void render () {
		super.render();
	}
}
