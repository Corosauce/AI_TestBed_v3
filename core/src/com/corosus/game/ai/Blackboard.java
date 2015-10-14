package com.corosus.game.ai;

import javax.vecmath.Vector2f;

public class Blackboard {

	private Vector2f posTarget = null;

	public Vector2f getPosTarget() {
		return posTarget;
	}

	public void setPosTarget(Vector2f posTarget) {
		this.posTarget = posTarget;
	}
	
	/**
	 * Main interface method to tell an entity to move to a position
	 * Will determine if it should request a path first or just move to it
	 * 
	 * @param pos
	 */
	public void moveTo(Vector2f pos) {
		//if within 2 tiles and both tiles have no collision then vec move
		//otherwise try to path
		//dont assume you can strait move to if you can see, we plan on PIT blocks
	}
	
}
