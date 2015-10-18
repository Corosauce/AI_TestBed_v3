package com.corosus.game.factory.spawnable;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Health;
import com.corosus.game.component.WeaponData;
import com.corosus.game.component.WeaponData.Weapon;
import com.corosus.game.factory.EntityFactory;

public class SpawnablePlayer implements SpawnableBase {

	@Override
	public List<Entity> prepareFromMap(int levelID, MapObject mapObj) {
		return null;
	}

	@Override
	public Entity prepareFromData(int levelID, Object... objects) {
		Entity ent = EntityFactory.createEntity_Player(levelID, (Float)objects[0], (Float)objects[1]);
		ent.getComponent(EntityData.class).setTeam((int) objects[2]);
		
		ent.getComponent(Health.class).setStartHealth(3000);
		
		Weapon weapon = new Weapon();
		weapon.projectileType = SpawnableTypes.PRJ_BULLET;
		weapon.ticksCooldownRate = 2;
		
		ent.getComponent(WeaponData.class).setActivePrimary(weapon);
		
		return ent;
	}
	
}
