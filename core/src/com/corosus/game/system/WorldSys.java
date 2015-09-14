package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;

public class WorldSys extends IntervalEntityProcessingSystem {
	
	public long gameTime = 0;
	
	public WorldSys(float interval) {
		super(Aspect.getEmpty(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		
		gameTime++;
		if (gameTime % 20 == 0) {
			System.out.println("spawn ent");
			Entity ent = new EntityBuilder(Game_AI_TestBed.instance().getWorld())
			.with(new Position(0, 0))
			.with(new Health(20))
			.build();
		}
		
		//super.processSystem();
	}

}
