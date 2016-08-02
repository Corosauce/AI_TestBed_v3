package com.corosus.game.ai.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;
import com.badlogic.gdx.ai.utils.random.IntegerDistribution;
import com.corosus.game.ai.Blackboard;

public class HuntForSurvivalNeeds extends LeafTask<Blackboard> {

	public HuntForSurvivalNeeds() {
	}
	
	@Override
	public void start () {
		super.start();
		
	}

	@Override
	public void run () {
		System.out.println("HuntForSurvivalNeeds");
		success();
	}

	@Override
	protected Task<Blackboard> copyTo (Task<Blackboard> task) {
		return task;
	}

}
