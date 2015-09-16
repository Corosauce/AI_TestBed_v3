package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;

public class SpriteProcessor extends IntervalEntityProcessingSystem {

	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Health> mapHealth;
	
	public SpriteProcessor(float interval) {
		super(Aspect.all(Position.class), interval);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		
	}

	@Override
	protected void process(Entity e) {
		
		//TODO: find a way to declare this at init without world NPE
		mapPos = ComponentMapper.getFor(Position.class, Game_AI_TestBed.instance().getWorld());
		mapHealth = ComponentMapper.getFor(Health.class, Game_AI_TestBed.instance().getWorld());
		
		//Position pos = mapPos.get(e);
		Health health = mapHealth.get(e);
		
		System.out.println("health: " + health.hp);
		
		health.hp--;
		
		if (health.hp <= 0) {
			System.out.println("killed entity");
			Game_AI_TestBed.instance().getWorld().deleteEntity(e);
		}
	}

}
