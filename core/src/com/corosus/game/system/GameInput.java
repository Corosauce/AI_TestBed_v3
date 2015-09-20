package com.corosus.game.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Input;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.Velocity;

@Wire
public class GameInput extends IntervalEntityProcessingSystem {
	
	public static HashMap<Integer, Boolean> lookupKeysDown = new HashMap<Integer, Boolean>();
	
	public GameInput(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		ComponentMapper<Velocity> mapVelocity = ComponentMapper.getFor(Velocity.class, Game_AI_TestBed.instance().getWorld());
		ComponentMapper<ProfileData> mapProfile = ComponentMapper.getFor(ProfileData.class, Game_AI_TestBed.instance().getWorld());
		
		Iterator<Entry<Integer, Boolean>> iter = lookupKeysDown.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Boolean> entry = iter.next();
			
			if (!entry.getValue()) continue;
			
			int keycode = entry.getKey();
			
			float speed = 15;
			
			if (keycode == Input.Keys.UP) {
				Game_AI_TestBed.instance().getCamera().translate(0, speed);
			} else if (keycode == Input.Keys.DOWN) {
				Game_AI_TestBed.instance().getCamera().translate(0, -speed);
			}
			
			if (keycode == Input.Keys.LEFT) {
				Game_AI_TestBed.instance().getCamera().translate(-speed, 0);
			} else if (keycode == Input.Keys.RIGHT) {
				Game_AI_TestBed.instance().getCamera().translate(speed, 0);
			}
			
			Entity player = Game_AI_TestBed.instance().getPlayerEntity();
			if (player != null) {
				
				Velocity vel = mapVelocity.get(player);
				ProfileData profile = mapProfile.get(player);
				
				if (keycode == Input.Keys.A) {
					vel.x -= profile.moveSpeed;
				} else if (keycode == Input.Keys.D) {
					vel.x += profile.moveSpeed;
				}
				
				if (keycode == Input.Keys.W) {
					vel.y += profile.moveSpeed;
				} else if (keycode == Input.Keys.S) {
					vel.y -= profile.moveSpeed;
				}
			}
			
			
			//if (entry.getKey() == )
		}
		
		//super.processSystem();
	}

}
