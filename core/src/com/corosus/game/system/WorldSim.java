package com.corosus.game.system;

import java.util.Random;

import javax.vecmath.Vector2f;

import net.mostlyoriginal.game.util.VecUtil;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Position;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.factory.EntityFactory;

@Wire
public class WorldSim extends IntervalEntityProcessingSystem {
	
	public long gameTime = 0;
	
	private ComponentMapper<Position> mapPos;
	
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
		Game_AI_TestBed.instance().getLevel().setGameTime(gameTime);
		if (gameTime % 3 == 0) {
			//System.out.println("spawn ent");
			for (int i = 0; i < 1; i++) {
				Random rand = new Random();
				
				int randX = rand.nextInt(Game_AI_TestBed.instance().getLevel().getLevelSizeX());
				int randY = rand.nextInt(Game_AI_TestBed.instance().getLevel().getLevelSizeY());
				
				Entity player = Game_AI_TestBed.instance().getLevel().getPlayerEntity();
				
				if (player != null) {
					
					Position posPlayer = mapPos.get(player);
					
					float dist = VecUtil.getDist(new Vector2f(randX, randY), new Vector2f(posPlayer.x, posPlayer.y));

					if (dist > 512) {
						EntityFactory.createEntity(EnumEntityType.SPRITE, randX, randY);
					}
				}
				
			}
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
