package com.corosus.game.ai.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;
import com.badlogic.gdx.ai.utils.random.IntegerDistribution;
import com.corosus.game.ai.Blackboard;

public class TestLeaf extends LeafTask<Blackboard> {

	@TaskAttribute
	public IntegerDistribution times = ConstantIntegerDistribution.ONE;

	private String wat;

	public TestLeaf(String wat) {
		this.wat = wat;
	}
	
	@Override
	public void start () {
		super.start();
		
	}

	@Override
	public void run () {
		System.out.println("test leaf run: " + wat);
		success();
	}

	@Override
	protected Task<Blackboard> copyTo (Task<Blackboard> task) {
		TestLeaf bark = (TestLeaf)task;
		bark.times = times;

		return task;
	}

}
