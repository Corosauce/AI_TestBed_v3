package com.corosus.game.entity;

import java.util.List;

import com.artemis.Component;

public class ActionRoutine {
	
	public int ticksMax = 10;
	public int ticksCur = 0;
	
	public ActionRoutine(int tickLength) {
		this.ticksMax = tickLength;
	}
	
	public void tick(List<Component> listComponents) {
		ticksCur++;
		if (!isComplete()) {
			tickAction(listComponents);
		}
	}
	
	public void tickAction(List<Component> listComponents) {
		
	}
	
	public boolean isComplete() {
		return ticksCur >= ticksMax;
	}
	
	public void dispose() {
		
	}
	
}