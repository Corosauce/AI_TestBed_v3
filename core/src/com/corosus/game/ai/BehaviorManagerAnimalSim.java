package com.corosus.game.ai;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.corosus.game.ai.btree.BooleanBranch;
import com.corosus.game.ai.btree.RandomTest;
import com.corosus.game.ai.btree.TestLeaf;

public class BehaviorManagerAnimalSim {

	private Agent agent;
	
	private BehaviorTree<Blackboard> btMain;
	
	private BehaviorTree<Blackboard> btSensing;
	
	public BehaviorManagerAnimalSim(Agent agent) {
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
		return btMain;
	}

	public void setBt(BehaviorTree<Blackboard> bt) {
		this.btMain = bt;
	}
	
	public void tick() {
		
		this.getAgent().getBlackboard().setIsFighting(true);
		
		btMain.step();
	}
	
	public void initTest() {
		
		/**
		 * 
		 * priorities:
		 * 
		 * - deal with potential attacks (fight back or flee)
		 * - drink water (higher if no cached water source locations)
		 * - eat food (higher if no cached food source locations)
		 * - wander
		 *
		 * 
		 * 
		 * 
		 * down = false, up = true
		 * 
		 * 					doSurvivalRoutine < 
		 * isThreatened <									doFightThreat 						
		 * 					doFightOrFlight | shouldFlee <
		 * 													doFleeThreat
         * 
         * 
         */
		
		Sequence<Blackboard> seq = new Sequence<>();
		seq.addChild(new BooleanBranch<Blackboard>(this.agent.getBlackboard().getIsFighting(), new TestLeaf("wat1"), new TestLeaf("wat2")));
		/*seq.addChild((new RandomTest()).setDebug("1"));
		seq.addChild((new RandomTest()).setDebug("2"));
		seq.addChild((new RandomTest()).setDebug("3"));*/
		

		
		btMain = new BehaviorTree<Blackboard>(seq);
		btMain.setObject(agent.getBlackboard());
	}
	
}
