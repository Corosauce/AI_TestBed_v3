package com.corosus.game.factory.spawnable;

import com.artemis.Entity;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.WeaponData;
import com.corosus.game.component.WeaponData.Weapon;

public class SpawnableSoldier extends SpawnableBaseNPC {

	@Override
	public Entity prepareFromData(Object... objects) {
		Entity ent = super.prepareFromData(objects);
		ent.getComponent(RenderData.class).setAsset("soldier");
		
		Weapon weapon = new Weapon();
		weapon.projectileType = SpawnableTypes.PRJ_PULSE;
		weapon.ticksCooldownRate = 20;
		
		ent.getComponent(WeaponData.class).setActivePrimary(weapon);
		
		return ent;
	}
	
}
