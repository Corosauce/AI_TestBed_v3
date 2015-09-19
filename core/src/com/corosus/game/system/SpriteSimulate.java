package com.corosus.game.system;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.component.Profile;
import com.corosus.game.component.Velocity;
import com.corosus.game.factory.EntityFactory;

public class SpriteSimulate extends IntervalEntityProcessingSystem {

	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Health> mapHealth;
	private ComponentMapper<Velocity> mapVelocity;
	private ComponentMapper<Profile> mapProfile;
	
	public SpriteSimulate(float interval) {
		super(Aspect.all(Position.class, Health.class, Velocity.class, Profile.class), interval);
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
		mapVelocity = ComponentMapper.getFor(Velocity.class, Game_AI_TestBed.instance().getWorld());
		mapProfile = ComponentMapper.getFor(Profile.class, Game_AI_TestBed.instance().getWorld());
		
		//Position pos = mapPos.get(e);
		Health health = mapHealth.get(e);
		Position pos = mapPos.get(e);
		Velocity motion = mapVelocity.get(e);
		Profile profile = mapProfile.get(e);
		
		Random rand = new Random();
		
		if (profile.profileID == 0) {
			motion.x = rand.nextInt(2)-rand.nextInt(2);
			motion.y = rand.nextInt(2)-rand.nextInt(2);
			
			//pos.x += 1F;
			//pos.y += rand.nextInt()-rand.nextInt();
			
			//System.out.println("health: " + health.hp);
			
			health.hp--;
			
			//if (rand.nextInt(10) == 0) {
			for (int i = 0; i < 25; i++) {
				float speed = 10F;
				float vecX = rand.nextFloat() * speed - rand.nextFloat() * speed;
				float vecY = rand.nextFloat() * speed - rand.nextFloat() * speed;
				EntityFactory.createEntity(1, pos.x, pos.y, vecX, vecY);
			}
		} else if (profile.profileID == 1) {
			if (health.lifeTime > 100) {
				Game_AI_TestBed.instance().killEntity(e);
			}
		}
		
		//physics
		pos.x += motion.x;
		pos.y += motion.y;
		
		float drag = 0.98F;
		
		motion.x *= drag;
		motion.y *= drag;
		
		health.lifeTime++;
		
		if (pos.x < 0 || pos.x > 3000 || pos.y < 0 || pos.y > 3000) {
			//System.out.println("killed out of bound entity");
			Game_AI_TestBed.instance().killEntity(e);
		}
		
		if (health.hp <= 0) {
			System.out.println("killed entity");
			Game_AI_TestBed.instance().killEntity(e);
		}
	}

}
