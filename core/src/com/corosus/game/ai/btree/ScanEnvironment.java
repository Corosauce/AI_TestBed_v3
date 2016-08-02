package com.corosus.game.ai.btree;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.ai.Blackboard;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Position;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.util.VecUtil;


public class ScanEnvironment extends LeafTask<Blackboard> {

	public ScanEnvironment() {
		
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
		
		Entity closestEnt = null;
		float closestDist = 9999999;
		
		Level level = Game_AI_TestBed.instance().getActiveLevel();
		for (int i = 0; i < 0; i++) {
			Entity entScan = level.getWorld().getEntity(i);
			
			EntityData dataScan = entScan.getComponent(EntityData.class);
			if (dataScan != null && dataScan.type == EnumEntityType.SPRITE) {
			
				Position posScan = entScan.getComponent(Position.class);
				
				float distToScanned = VecUtil.getDist(new Vector2f(posThis.x, posThis.y), new Vector2f(posScan.x, posScan.y));
				if (distToScanned < 80) {
					if (VecUtil.canSee(level.getLevelID(), posThis.toVec(), posScan.toVec())) {
						if (distToScanned < closestDist) {
							closestDist = distToScanned;
							closestEnt = entScan;
						}
					}
				}
			}
		}
		
		if (closestEnt != null) {
			this.getObject().setClosestPossibleThreatID(closestEnt.getId());
		} else {
			this.getObject().setClosestPossibleThreatID(-1);
		}
		
		success();
	}

	@Override
	protected Task<Blackboard> copyTo (Task<Blackboard> task) {
		return task;
	}

}
