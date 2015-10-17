package com.corosus.game.ai;

import java.util.List;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.ai.pathfind.PathfinderHelper;
import com.corosus.game.component.Position;
import com.corosus.game.util.IntPair;
import com.corosus.game.util.VecUtil;

public class Blackboard {
	
	private Agent agent;

	private int targetID = -1;
	
	private Vector2f posTarget = null;
	private long pfTimeCooldown = 40;
	private long lastPFTime = -pfTimeCooldown;
	
	private List<IntPair> listPath = null;
	private int indexPath = 0;

	public Blackboard(Agent agent) {
		this.setAgent(agent);
	}
	
	public Vector2f getPosTarget() {
		return posTarget;
	}

	public void setPosTarget(Vector2f posTarget) {
		this.posTarget = posTarget;
	}

	public int getTargetID() {
		return targetID;
	}

	public void setTargetID(int targetID) {
		this.targetID = targetID;
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
			//resetPath();
		} else {
			//pathfind
			IntPair coordFrom = new IntPair(posData.x / (float)Cst.TILESIZE, posData.y / (float)Cst.TILESIZE);
			IntPair coordTo = new IntPair(pos.x / (float)Cst.TILESIZE, pos.y / (float)Cst.TILESIZE);
			
			Level level = Game_AI_TestBed.instance().getLevel(agent.getLevelID());
			if (level.isPassable(coordFrom.x, coordFrom.y) && (lastPFTime + pfTimeCooldown <= level.getTime() || !hasPath())) {
				if (level.isPassable(coordTo.x, coordTo.y)) {
					//Logger.dbg("pathfind try!");
					
					lastPFTime = level.getTime();
					setPath(PathfinderHelper.instance().getPath(agent.getLevelID(), coordFrom, coordTo));
					
					//System.out.println("path size: " + listPath.size());
				}
			}
		}
		
		
	}
	
	public void setPath(List<IntPair> path) {
		this.listPath = path;
		indexPath = 0;
	}
	
	public boolean hasPath() {
		return listPath != null && listPath.size() > 0;
	}
	
	public IntPair getPathPoint() {
		return listPath.get(indexPath);
	}
	
	public void incPathPoint() {
		indexPath++;
		if (indexPath >= listPath.size()) {
			resetPath();
		}
	}
	
	public void resetPath() {
		listPath = null;
		indexPath = 0;
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
