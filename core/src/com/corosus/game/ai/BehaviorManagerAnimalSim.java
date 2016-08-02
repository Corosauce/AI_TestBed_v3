package com.corosus.game.ai;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.corosus.game.ai.btree.BooleanBranch;
import com.corosus.game.ai.btree.Flee;
import com.corosus.game.ai.btree.HuntForSurvivalNeeds;
import com.corosus.game.ai.btree.RandomTest;
import com.corosus.game.ai.btree.ScanEnvironment;
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
		 * up = false, down = true
		 * 
		 * 					doSurvivalRoutine < 
		 * isThreatened <									doFightThreat 						
		 * 					doFightOrFlight | shouldFlee <
		 * 													doFleeThreat
         * 
         * 
         */
		
		//TODO: do i need to set blackboard on each object or just the top parent node?
		
		BooleanBranch<Blackboard> shouldFlee = new BooleanBranch<Blackboard>(agent.getBlackboard().getShouldFlee());
		shouldFlee.addChild(new TestLeaf("TODO: attack or something"));
		shouldFlee.addChild(new Flee());
		
		BooleanBranch<Blackboard> isThreatened = new BooleanBranch<Blackboard>(agent.getBlackboard().getIsFighting());
		
		isThreatened.addChild(new HuntForSurvivalNeeds());
		isThreatened.addChild(shouldFlee);
		
		Sequence<Blackboard> seqMain = new Sequence<Blackboard>();
		seqMain.addChild(isThreatened);
		
		Sequence<Blackboard> seqSense = new Sequence<Blackboard>();
		seqSense.addChild(new ScanEnvironment());
		//seq.addChild(new BooleanBranch<Blackboard>(this.agent.getBlackboard().getIsFighting(), new TestLeaf("wat1"), new TestLeaf("wat2")));
		/*seq.addChild((new RandomTest()).setDebug("1"));
		seq.addChild((new RandomTest()).setDebug("2"));
		seq.addChild((new RandomTest()).setDebug("3"));*/
		

		
		btMain = new BehaviorTree<Blackboard>(seqMain);
		btMain.setObject(agent.getBlackboard());
		
		btSensing = new BehaviorTree<Blackboard>(seqSense);
		btSensing.setObject(agent.getBlackboard());
	}
	
}
