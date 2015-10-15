package com.corosus.game.ai;

public class Agent {

	private int levelID = 0;
	private int entID = -1;
	
	private Blackboard aiBlackboard;
	
	public Agent(int levelID, int entID) {
		setLevelID(levelID);
		setEntID(entID);
		setAIBlackboard(new Blackboard(this));
	}

	public Blackboard getAIBlackboard() {
		return aiBlackboard;
	}

	public void setAIBlackboard(Blackboard aiBlackboard) {
		this.aiBlackboard = aiBlackboard;
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
	}
	
}
