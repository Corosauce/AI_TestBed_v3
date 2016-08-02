package com.corosus.game.ai;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.vecmath.Vector2f;

import com.corosus.game.util.IntPair;

public class Blackboard {
	
	private Agent agent;

	private int targetID = -1;
	
	private Vector2f posTarget = null;
	public long pfTimeCooldown = 40;
	public long lastPFTime = -pfTimeCooldown;
	
	private List<IntPair> listPath = null;
	private int indexPath = 0;
	
	private AtomicBoolean isFighting = new AtomicBoolean(false);
	private AtomicBoolean shouldTrySurvival = new AtomicBoolean(false);
	private AtomicBoolean shouldFlee = new AtomicBoolean(false);

	private AtomicBoolean shouldWander = new AtomicBoolean(false);
	
	private int closestPossibleThreatID = -1;

	public int getClosestPossibleThreatID() {
		return closestPossibleThreatID;
	}

	public void setClosestPossibleThreatID(int closestPossibleThreatID) {
		this.closestPossibleThreatID = closestPossibleThreatID;
	}

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

	public AtomicBoolean getIsFighting() {
		return isFighting;
	}

	public void setIsFighting(boolean isFighting) {
		this.isFighting.set(isFighting);
	}

	public AtomicBoolean getShouldTrySurvival() {
		return shouldTrySurvival;
	}

	public void setShouldTrySurvival(boolean shouldTrySurvival) {
		this.shouldTrySurvival.set(shouldTrySurvival);
	}

	public AtomicBoolean getShouldWander() {
		return shouldWander;
	}

	public void setShouldWander(boolean shouldWander) {
		this.shouldWander.set(shouldWander);
	}
	
	public AtomicBoolean getShouldFlee() {
		return shouldFlee;
	}

	public void setShouldFlee(boolean shouldFlee) {
		this.shouldFlee.set(shouldFlee);
	}
	
}
