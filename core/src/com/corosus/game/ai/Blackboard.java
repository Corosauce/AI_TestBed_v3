package com.corosus.game.ai;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.Logger;
import com.corosus.game.ai.pathfind.PathfinderHelper;
import com.corosus.game.component.Position;
import com.corosus.game.util.IntPair;
import com.corosus.game.util.VecUtil;

public class Blackboard {
	
	private Agent agent;

	private Vector2f posTarget = null;

	public Blackboard(Agent agent) {
		this.setAgent(agent);
	}
	
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
		
		
		
		Position posData = getEntity().getComponent(Position.class);
		
		float dist = VecUtil.getDist(pos, posData.toVec());
		if (dist < Cst.TILESIZE * 2) {
			setPosTarget(pos);
		} else {
			//pathfind
			IntPair coordFrom = new IntPair(posData.x / (float)Cst.TILESIZE, posData.y / (float)Cst.TILESIZE);
			IntPair coordTo = new IntPair(pos.x / (float)Cst.TILESIZE, pos.y / (float)Cst.TILESIZE);
			
			Level level = Game_AI_TestBed.instance().getLevel(agent.getLevelID());
			if (level.getTime() % 40 == 0 && level.isPassable(coordFrom.x, coordFrom.y)) {
				if (level.isPassable(coordTo.x, coordTo.y)) {
					Logger.dbg("pathfind try!");
					
					PathfinderHelper.instance().getPath(agent.getLevelID(), coordFrom, coordTo);
				}
			}
		}
		
		
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public Entity getEntity() {
		return Game_AI_TestBed.instance().getLevel(agent.getLevelID()).getWorld().getEntity(agent.getEntID());
	}
	
}
