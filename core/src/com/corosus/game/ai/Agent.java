package com.corosus.game.ai;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Position;
import com.corosus.game.util.IntPair;
import com.corosus.game.util.VecUtil;

public class Agent {

	private int levelID = 0;
	private int entID = -1;
	
	private Blackboard blackboard;
	
	public Agent(int levelID, int entID) {
		setLevelID(levelID);
		setEntID(entID);
		setAIBlackboard(new Blackboard(this));
	}

	public Blackboard getAIBlackboard() {
		return blackboard;
	}

	public void setAIBlackboard(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	public int getEntID() {
		return entID;
	}

	public void setEntID(int entID) {
		this.entID = entID;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}
	
	public void tick() {
		//System.out.println("AI tick!");
		
		Entity ent = Game_AI_TestBed.instance().getLevel(levelID).getWorld().getEntity(entID);
		Position pos = ent.getComponent(Position.class);
		
		//pathfollow
		if (blackboard.hasPath()) {
			IntPair pointTile = blackboard.getPathPoint();
			Vector2f pointCoord = new Vector2f((pointTile.x) + (Cst.TILESIZE / 2), (pointTile.y) + (Cst.TILESIZE / 2));
			
			float dist = VecUtil.getDist(pointCoord, pos.toVec());
			
			blackboard.moveTo(pointCoord);
			
			if (dist < 8) {
				//System.out.println("inc point!");
				blackboard.incPathPoint();
			} else {
				//System.out.println("pathnav to " + dist);
				
			}
		} else {
			//System.out.println("no path");
		}
	}
	
}
