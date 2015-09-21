package com.corosus.game.client.screen;

import com.corosus.game.Game_AI_TestBed;

public class ScreenActiveGame extends ScreenBase {

	@Override
	public void render(float delta) {
		Game_AI_TestBed.instance().process(delta);
	}

}
