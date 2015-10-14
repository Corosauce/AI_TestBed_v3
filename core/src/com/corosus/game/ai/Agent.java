package com.corosus.game.ai;

public class Agent {

	private Blackboard aiBlackboard;
	
	public Agent() {
		setAIBlackboard(new Blackboard());
	}

	public Blackboard getAIBlackboard() {
		return aiBlackboard;
	}

	public void setAIBlackboard(Blackboard aiBlackboard) {
		this.aiBlackboard = aiBlackboard;
	}
	
}
