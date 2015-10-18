package com.corosus.game.system;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.PhysicsData;

public class PhysicsWrapper extends IntervalEntityProcessingSystem {
	
	//in an attempt to solve a box2d createBody crash this removal queue was added, was most likely pointless
	//crashes are related to projectiles / collisions and their deaths somehow
	public static List<PhysicsData> listBodiesToRemove = new ArrayList<PhysicsData>();
	
	public PhysicsWrapper(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		//For last 2 params: making these values higher will give you a more correct simulation, at the cost of some performance.
		Game_AI_TestBed.instance().getActiveLevel().getWorldBox2D().step(GameSettings.tickDelayGame, 6, 2);
		for (PhysicsData data : listBodiesToRemove) {
			//TODO: multi world support for box2d
			data.disposeBox2D(0);
		}
		listBodiesToRemove.clear();
		//Game_AI_TestBed.instance().getLevel().getWorldBox2D().step(GameSettings.tickDelayGame, 1, 1);
		
		super.processSystem();
	}
	
	public static void pendRemoveBody(PhysicsData data) {
		listBodiesToRemove.add(data);
	}

}
