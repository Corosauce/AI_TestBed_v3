package com.corosus.game.system;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Logger;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.component.RenderData;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.factory.EntityFactory;

public class WorldSim extends IntervalEntityProcessingSystem {
	
	public long gameTime = 0;
	
	public WorldSim(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		//Logger.dbg("tick " + this);
		
		gameTime++;
		if (gameTime % 2 == 0) {
			//System.out.println("spawn ent");
			Random rand = new Random();
			EntityFactory.createEntity(EnumEntityType.SPRITE, rand.nextInt(Game_AI_TestBed.instance().getLevel().getLevelSizeX()), rand.nextInt(Game_AI_TestBed.instance().getLevel().getLevelSizeY()));
			/*Entity ent = new EntityBuilder(Game_AI_TestBed.instance().getWorld())
			.with(new Position(rand.nextInt(1000), rand.nextInt(1000)))
			.with(new Health(20))
			.with(new RenderData())
			.build();*/
		}
		
		//System.out.println("entities: " + Game_AI_TestBed.instance().entityCount);
		
		//super.processSystem();
	}

}
