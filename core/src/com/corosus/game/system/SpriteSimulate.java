package com.corosus.game.system;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Velocity;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.factory.EntityFactory;

@Wire
public class SpriteSimulate extends IntervalEntityProcessingSystem {

	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Health> mapHealth;
	private ComponentMapper<Velocity> mapVelocity;
	private ComponentMapper<EntityData> mapProfile;
	
	public SpriteSimulate(float interval) {
		super(Aspect.all(Position.class, Health.class, Velocity.class, EntityData.class), interval);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		
	}

	@Override
	protected void process(Entity e) {
		
		//Position pos = mapPos.get(e);
		Health health = mapHealth.get(e);
		Position pos = mapPos.get(e);
		Velocity motion = mapVelocity.get(e);
		EntityData profile = mapProfile.get(e);
		
		Random rand = new Random();
		
		if (profile.type == EnumEntityType.SPRITE) {
			if (profile.aiControlled) {
				motion.x = rand.nextInt(2)-rand.nextInt(2);
				motion.y = rand.nextInt(2)-rand.nextInt(2);
				
				health.hp--;
				
				//if (rand.nextInt(10) == 0) {
				for (int i = 0; i < 25; i++) {
					float speed = 10F;
					float vecX = rand.nextFloat() * speed - rand.nextFloat() * speed;
					float vecY = rand.nextFloat() * speed - rand.nextFloat() * speed;
					//EntityFactory.createEntity(EnumEntityType.PROJECTILE, pos.x, pos.y, vecX, vecY);
				}
			} else {
				
			}
		} else if (profile.type == EnumEntityType.PROJECTILE) {
			if (health.lifeTime > 100) {
				Game_AI_TestBed.instance().getLevel().killEntity(e);
			}
		}
		
		//physics
		pos.x += motion.x;
		pos.y += motion.y;
		
		float drag = 0.15F;
		
		motion.x *= drag;
		motion.y *= drag;
		
		health.lifeTime++;
		
		if (pos.x < 0) {
			pos.x = 0;
		}
		
		if (pos.x > Game_AI_TestBed.instance().getLevel().getLevelSizeX()) {
			pos.x = Game_AI_TestBed.instance().getLevel().getLevelSizeX();
		}
		
		if (pos.y < 0) {
			pos.y = 0;
		}
		
		if (pos.y > Game_AI_TestBed.instance().getLevel().getLevelSizeY()) {
			pos.y = Game_AI_TestBed.instance().getLevel().getLevelSizeY();
		}
		
		/*if (pos.x < 0 || pos.x > Game_AI_TestBed.instance().getLevel().getLevelSizeX() || pos.y < 0 || pos.y > Game_AI_TestBed.instance().getLevel().getLevelSizeY()) {
			//System.out.println("killed out of bound entity");
			Game_AI_TestBed.instance().getLevel().killEntity(e);
		}*/
		
		if (health.hp <= 0) {
			System.out.println("killed entity");
			Game_AI_TestBed.instance().getLevel().killEntity(e);
		}
	}

}
