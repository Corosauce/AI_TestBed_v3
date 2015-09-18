package com.corosus.game.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Input;
import com.corosus.game.Game_AI_TestBed;

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
			//if (entry.getKey() == )
		}
		
		//super.processSystem();
	}

}
