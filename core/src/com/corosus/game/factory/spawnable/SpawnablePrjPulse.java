package com.corosus.game.factory.spawnable;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Velocity;
import com.corosus.game.factory.EntityFactory;

public class SpawnablePrjPulse implements SpawnableBase {

	@Override
	public List<Entity> prepareFromMap(MapObject mapObj) {

		return null;
	}

	@Override
	public Entity prepareFromData(Object... objects) {
		Entity ent = EntityFactory.createEntity_Projectile((Float)objects[0], (Float)objects[1]);
		ent.getComponent(EntityData.class).setTeam((int) objects[2]);
		ent.getComponent(Velocity.class).set((Float)objects[3], (Float)objects[4]);
		return ent;
	}
	
}
