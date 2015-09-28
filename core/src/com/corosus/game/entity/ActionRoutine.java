package com.corosus.game.entity;

import java.util.List;

import com.artemis.Component;

public class ActionRoutine {
	
	//counts down
	public int ticksCur = 0;
	public int ticksMax = 10;
	
	//counts down
	public int ticksCooldownCur = 0;
	public int ticksCooldownMax = 10;
	
	//lets action reset to active if still in the middle of being active
	public boolean canReInitWhileActive = false;
	
	public ActionRoutine(int tickLength) {
		this.ticksMax = tickLength;
	}
	
	public void tick(List<Component> listComponents) {
		
		if (isActive()) {
			ticksCur--;
			tickAction(listComponents);
			
			if (!isActive()) {
				endRoutine();
			}
		} else {
			if (ticksCooldownCur > 0) {
				ticksCooldownCur--;
			}
		}
	}
	
	public void tickAction(List<Component> listComponents) {
		
	}
	
	public boolean isActive() {
		return ticksCur > 0;
	}
	
	public boolean isCoolingDown() {
		return ticksCooldownCur > 0;
	}
	
	public void startRoutine() {
		ticksCur = ticksMax;
	}
	
	public void endRoutine() {
		ticksCur = 0;
		ticksCooldownCur = ticksCooldownMax;
	}
	
	public boolean tryActivate(Object... objects) {
		if (isActive() && !canReInitWhileActive) {
			return false;
		}
		
		if (isCoolingDown()) {
			return false;
		}
		
		ticksCur = ticksMax;
		
		return true;
	}
	
	public void dispose() {
		
	}
	
}