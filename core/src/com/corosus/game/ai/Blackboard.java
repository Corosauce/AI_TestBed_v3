package com.corosus.game.ai;

import java.util.List;

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
	
}
