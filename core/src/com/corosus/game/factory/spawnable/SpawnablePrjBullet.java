package com.corosus.game.factory.spawnable;

import com.artemis.Entity;
import com.corosus.game.component.ProjectileData;
import com.corosus.game.component.RenderData;

public class SpawnablePrjBullet extends SpawnableBaseProjectile {

	@Override
	public Entity prepareFromData(int levelID, Object... objects) {
		Entity ent = super.prepareFromData(levelID, objects);
		
		ent.getComponent(RenderData.class).renderType = RenderData.TYPE_LINE;
		ent.getComponent(ProjectileData.class).prjDamage = 5;
		
		return ent;
	}
	
}
