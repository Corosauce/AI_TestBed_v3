package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.Game_AI_TestBed;

public class WorldTimer extends IntervalEntityProcessingSystem {
	
	public long gameTime = 0;
	public static float lastTime = 0;
	
	public WorldTimer(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		gameTime++;
		lastTime = Game_AI_TestBed.instance().getActiveLevel().getStateTime();
		
		super.processSystem();
	}

}
