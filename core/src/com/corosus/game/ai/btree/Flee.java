package com.corosus.game.ai.btree;

import java.util.Random;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.ai.Blackboard;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Position;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.util.VecUtil;


public class Flee extends LeafTask<Blackboard> {

	public Flee() {
		
	}
	
	@Override
	public void start () {
		super.start();
		
	}

	@Override
	public void run () {
		
		//get closest visible entity, inefficiently
		
		Entity entThis = this.getObject().getAgent().getEntity();
		Position posThis = entThis.getComponent(Position.class);
		
		if (getObject().getClosestPossibleThreatID() == -1) {
			fail();
			return;
		}
		
		Level level = Game_AI_TestBed.instance().getActiveLevel();
		Entity entTarg = level.getWorld().getEntity(getObject().getClosestPossibleThreatID());
		
		//if entity ID is invalid, force fail and also reset target data
		if (entTarg == null) {
			getObject().setClosestPossibleThreatID(-1);
			fail();
			return;
		}
		
		Position posTarg = entTarg.getComponent(Position.class);
		
		float distToScanned = VecUtil.getDist(new Vector2f(posThis.x, posThis.y), new Vector2f(posTarg.x, posTarg.y));
		
		if (distToScanned <= 80) {
			float vecX = posTarg.x - posThis.x;
			float vecY = posTarg.y - posThis.y;
			
			//vec could work if only strait line, but we want angle variance
			//Vector2f vec = VecUtil.getTargetVector(posThis.toVec(), posTarg.toVec());
			
			Random rand = new Random();
			int randAngleSize = 30;
			double angleDeg = Math.toDegrees(Math.atan2(vecX, vecY));
			angleDeg += rand.nextInt(randAngleSize) - randAngleSize / 2;
			double angleRad = Math.toRadians(angleDeg);
			float navDist = Cst.TILESIZE * 5;
			
			Vector2f moveTo = new Vector2f(posThis.x + ((float)Math.sin(angleRad) * navDist), posThis.y + ((float)Math.cos(angleRad) * navDist));
			
			getObject().getAgent().moveTo(moveTo);
		}
		
		success();
	}

	@Override
	protected Task<Blackboard> copyTo (Task<Blackboard> task) {
		return task;
	}

}
