package com.corosus.game.ai.btree;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.ai.btree.SingleRunningChildBranch;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.btree.annotation.TaskConstraint;
import com.badlogic.gdx.utils.Array;

/**
 *
 * Ticks first child if boolRef is false, second if true
 * Uses AtomicBoolean for sake of its mutable nature, so can be used as reference
 * For easy wiring and templating of a behavior tree for a blackboard AtomicBoolean
 * 
 * @author Corosus
 *
 * @param <E>
 */
@TaskConstraint(minChildren = 1, maxChildren = 2)
public class BooleanBranch<E> extends SingleRunningChildBranch<E> {

	@TaskAttribute(required = true)
	AtomicBoolean boolRef;
	
	public BooleanBranch (AtomicBoolean boolRef) {
		super();
		this.boolRef = boolRef;
	}

	/** Creates a {@code Sequence} branch with the given children.
	 * 
	 * @param tasks the children of this task */
	public BooleanBranch (AtomicBoolean boolRef, Array<Task<E>> tasks) {
		super(tasks);
		this.boolRef = boolRef;
	}

	/** Creates a {@code Sequence} branch with the given children.
	 * 
	 * @param tasks the children of this task */
	public BooleanBranch (AtomicBoolean boolRef, Task<E>... tasks) {
		super(new Array<Task<E>>(tasks));
		this.boolRef = boolRef;
	}
	
	@Override
	public void start() {
		super.start();
		if (!boolRef.get()) {
			currentChildIndex = 0;
		} else {
			currentChildIndex = 1;
		}
	}

	@Override
	public void childSuccess (Task<E> runningTask) {
		super.childSuccess(runningTask);
		if (!boolRef.get()) {
			currentChildIndex = 0;
		} else {
			currentChildIndex = 1;
		}
		/*if (++currentChildIndex < children.size) {
			run();
		} else {
			success();
		}*/
	}
	
	@Override
	public void run() {
		System.out.println("running BooleanBranch, cur index: " + currentChildIndex);
		super.run();
	}

	@Override
	public void childFail (Task<E> runningTask) {
		super.childFail(runningTask);
		fail();
	}

}
