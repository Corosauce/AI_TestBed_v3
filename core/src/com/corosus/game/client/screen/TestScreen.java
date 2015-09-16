package com.corosus.game.client.screen;

import com.badlogic.gdx.Screen;
import com.corosus.game.Game_AI_TestBed;

public class TestScreen implements Screen {

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Game_AI_TestBed.instance().process(delta);
		
		Game_AI_TestBed.instance().render(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
	}

}
