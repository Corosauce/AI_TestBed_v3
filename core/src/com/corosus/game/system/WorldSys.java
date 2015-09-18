package com.corosus.game.system;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.component.RenderData;

public class WorldSys extends IntervalEntityProcessingSystem {
	
	public long gameTime = 0;
	
	public WorldSys(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		
		gameTime++;
		if (gameTime % 2 == 0) {
			System.out.println("spawn ent");
			Random rand = new Random();
			Entity ent = new EntityBuilder(Game_AI_TestBed.instance().getWorld())
			.with(new Position(rand.nextInt(100), rand.nextInt(100)))
			.with(new Health(20))
			.with(new RenderData())
			.build();
		}
		
		//super.processSystem();
	}

}
