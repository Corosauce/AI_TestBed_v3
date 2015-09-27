package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;

public class PhysicsWrapper extends IntervalEntityProcessingSystem {
	
	public PhysicsWrapper(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		//For last 2 params: making these values higher will give you a more correct simulation, at the cost of some performance.
		Game_AI_TestBed.instance().getLevel().getWorldBox2D().step(GameSettings.tickDelayGame, 6, 2);
		
		super.processSystem();
	}

}
