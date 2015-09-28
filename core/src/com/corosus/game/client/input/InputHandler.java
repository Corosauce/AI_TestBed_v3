package com.corosus.game.client.input;

import com.badlogic.gdx.InputAdapter;
import com.corosus.game.Logger;
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
		
		Logger.dbg("mouse moved!");
		
		GameInput.mouseX = screenX;
		GameInput.mouseY = screenY;
		
		return super.mouseMoved(screenX, screenY);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		Logger.dbg("mouse down!");
		
		GameInput.mouseX = screenX;
		GameInput.mouseY = screenY;
		
		GameInput.lookupMouseDown.put(button, true);
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		GameInput.mouseX = screenX;
		GameInput.mouseY = screenY;
		
		GameInput.lookupMouseDown.put(button, false);
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		GameInput.mouseX = screenX;
		GameInput.mouseY = screenY;
		
		return super.touchDragged(screenX, screenY, pointer);
	}
	
}
