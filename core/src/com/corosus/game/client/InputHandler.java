package com.corosus.game.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.system.GameInput;

public class InputHandler extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		
		GameInput.lookupKeysDown.put(keycode, true);
		
		return super.keyDown(keycode);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		
		GameInput.lookupKeysDown.put(keycode, false);
		
		return super.keyUp(keycode);
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		GameInput.mouseX = screenX;
		GameInput.mouseY = screenY;
		
		return super.mouseMoved(screenX, screenY);
	}
	
	
	
}
