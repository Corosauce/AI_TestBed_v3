package com.corosus.game.ai.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.corosus.game.Logger;
import com.corosus.game.ai.Blackboard;

public class RandomTest extends LeafTask<Blackboard> {

	private float randChance = 0.2F;
	private String debug = "";
	
	public RandomTest() {
		
	}
	
	public RandomTest setDebug(String debug) {
		this.debug = debug;
		return this;
	}
	
	@Override
	public void run() {
		if (Math.random() < randChance) {
			Logger.dbg("hit success chance, dbg: " + debug);
			success();
		} else {
			Logger.dbg("still executing RandomChance, dbg: " + debug);
		}

	}

	@Override
	protected Task<Blackboard> copyTo(Task<Blackboard> task) {
		RandomTest test = (RandomTest) task;
		test.randChance = randChance;
		return task;
	}

}
