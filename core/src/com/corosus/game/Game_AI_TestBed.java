package com.corosus.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Game;
import com.corosus.game.client.screen.TestScreen;
import com.corosus.game.system.SpriteProcessor;
import com.corosus.game.system.WorldSys;

public class Game_AI_TestBed extends Game {

	private static Game_AI_TestBed instance;
	
	private World world;
	
	public static Game_AI_TestBed instance() {
		return instance;
	}
	
	@Override
	public void create() {
		instance = this;

		restart();
	}
	
	public void restart() {
		
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new SpriteProcessor(GameSettings.tickDelayGame));
		worldConfig.setSystem(new WorldSys(GameSettings.tickDelayGame));
		
		world = new World(worldConfig);
		
		setScreen(new TestScreen());
		
	}
	
	public World getWorld() {
		return world;
	}
	
	public void process(float delta) {
		world.setDelta(delta);
		world.process();
	}

}
