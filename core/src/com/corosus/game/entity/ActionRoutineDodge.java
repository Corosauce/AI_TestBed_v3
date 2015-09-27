package com.corosus.game.entity;

import java.util.List;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.component.Velocity;

public class ActionRoutineDodge extends ActionRoutine {

	public Vector2 motion;
	
	public ActionRoutineDodge(int tickLength, Vector2 motion) {
		super(tickLength);
		this.motion = motion;
	}
	
	@Override
	public void tickAction(List<Component> listComponents) {
		super.tickAction(listComponents);
		
		Velocity vel = (Velocity) listComponents.get(0);
		
		vel.x = motion.x;
		vel.y = motion.y;
	}
	
}
