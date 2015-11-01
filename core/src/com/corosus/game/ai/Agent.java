package com.corosus.game.ai;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.ai.pathfind.PathfinderHelper;
import com.corosus.game.component.Position;
import com.corosus.game.util.IntPair;
import com.corosus.game.util.VecUtil;

public class Agent {

	private int levelID = 0;
	private int entID = -1;
	
	private Blackboard blackboard;
	
	private BehaviorManagerTest behavior;
	
	public Agent(int levelID, int entID) {
		setLevelID(levelID);
		setEntID(entID);
		setAIBlackboard(new Blackboard(this));
		setBehavior(new BehaviorManagerTest(this));
	}

	public Blackboard getBlackboard() {
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
	
	public Entity getEntity() {
		return Game_AI_TestBed.instance().getLevel(getLevelID()).getWorld().getEntity(getEntID());
	}
	
	public void tick() {
		//System.out.println("AI tick!");
		
		Entity ent = Game_AI_TestBed.instance().getLevel(levelID).getWorld().getEntity(entID);
		Position pos = ent.getComponent(Position.class);
		
		int pathFollowAccuracy = 4;
		
		//pathfollow
		if (blackboard.hasPath()) {
			IntPair pointTile = blackboard.getPathPoint();
			Vector2f pointCoord = new Vector2f((pointTile.x * Cst.TILESIZE) + (Cst.TILESIZE / 2), (pointTile.y * Cst.TILESIZE) + (Cst.TILESIZE / 2));
			
			float dist = VecUtil.getDist(pointCoord, pos.toVec());
			
			//was moveTo
			blackboard.setPosTarget(pointCoord);
			
			if (dist < pathFollowAccuracy) {
				//System.out.println("inc point!");
				blackboard.incPathPoint();
			} else {
				//System.out.println("pathnav to " + dist);
				
			}
		} else {
			//System.out.println("no path");
		}
		
		//test
		behavior.tick();
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
			blackboard.setPosTarget(pos);
			//resetPath();
		} else {
			//pathfind
			IntPair coordFrom = new IntPair(posData.x / (float)Cst.TILESIZE, posData.y / (float)Cst.TILESIZE);
			IntPair coordTo = new IntPair(pos.x / (float)Cst.TILESIZE, pos.y / (float)Cst.TILESIZE);
			
			Level level = Game_AI_TestBed.instance().getLevel(getLevelID());
			if (level.isPassable(coordFrom.x, coordFrom.y) && (blackboard.lastPFTime + blackboard.pfTimeCooldown <= level.getTime() || !blackboard.hasPath())) {
				if (level.isPassable(coordTo.x, coordTo.y)) {
					//Logger.dbg("pathfind try!");
					
					blackboard.lastPFTime = level.getTime();
					blackboard.setPath(PathfinderHelper.instance().getPath(getLevelID(), coordFrom, coordTo));
					
					//attempt to skip first node if we are close to it so we dont backtrack
					//this might sabatoge pathfollowing, keep eye on it
					if (blackboard.hasPath()) {
						IntPair pointTile = blackboard.getPathPoint();
						Vector2f pointCoord = new Vector2f((pointTile.x * Cst.TILESIZE) + (Cst.TILESIZE / 2), (pointTile.y * Cst.TILESIZE) + (Cst.TILESIZE / 2));
						
						float distToNode = VecUtil.getDist(pointCoord, posData.toVec());
						if (distToNode <= Cst.TILESIZE) {
							//Logger.dbg("skip first node!");
							blackboard.incPathPoint();
						}
					}
					
					//System.out.println("path size: " + listPath.size());
				}
			}
		}
		
		
	}

	public BehaviorManagerTest getBehavior() {
		return behavior;
	}

	public void setBehavior(BehaviorManagerTest behavior) {
		this.behavior = behavior;
	}
	
}
