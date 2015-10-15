package com.corosus.game.factory.spawnable;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.ProjectileData;
import com.corosus.game.component.Velocity;
import com.corosus.game.factory.EntityFactory;

public class SpawnableBaseProjectile implements SpawnableBase {

	@Override
	public List<Entity> prepareFromMap(int levelID, MapObject mapObj) {

		return null;
	}

	@Override
	public Entity prepareFromData(int levelID, Object... objects) {
		Entity ent = EntityFactory.createEntity_Projectile(levelID, (Float)objects[0], (Float)objects[1]);
		ent.getComponent(EntityData.class).setTeam((int) objects[2]);
		
		ProjectileData data = ent.getComponent(ProjectileData.class);
		
		ent.getComponent(Velocity.class).set((Float)objects[3] * data.speed, (Float)objects[4] * data.speed);
		return ent;
	}
	
}
