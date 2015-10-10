package com.corosus.game.factory.spawnable;

import com.artemis.Entity;
import com.corosus.game.component.RenderData;

public class SpawnableGeneral extends SpawnableBaseNPC {

	@Override
	public Entity prepareFromData(Object... objects) {
		Entity ent = super.prepareFromData(objects);
		ent.getComponent(RenderData.class).setAsset("general");
		return ent;
	}
	
}
