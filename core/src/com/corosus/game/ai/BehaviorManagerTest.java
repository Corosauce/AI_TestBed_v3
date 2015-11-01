package com.corosus.game.ai;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.corosus.game.ai.btree.RandomTest;

public class BehaviorManagerTest {

	private Agent agent;
	
	private BehaviorTree<Blackboard> bt;
	
	public BehaviorManagerTest(Agent agent) {
		setAgent(agent);
		
		initTest();
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public BehaviorTree<Blackboard> getBt() {
		return bt;
	}

	public void setBt(BehaviorTree<Blackboard> bt) {
		this.bt = bt;
	}
	
	public void tick() {
		bt.step();
	}
	
	public void initTest() {
		
		Sequence<Blackboard> seq = new Sequence<>();
		seq.addChild((new RandomTest()).setDebug("1"));
		seq.addChild((new RandomTest()).setDebug("2"));
		seq.addChild((new RandomTest()).setDebug("3"));
		

		
		bt = new BehaviorTree<Blackboard>(seq);
		bt.setObject(agent.getBlackboard());
	}
	
}
