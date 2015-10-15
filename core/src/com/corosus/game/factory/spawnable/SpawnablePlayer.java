package com.corosus.game.factory.spawnable;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.corosus.game.component.EntityData;
import com.corosus.game.factory.EntityFactory;

public class SpawnablePlayer implements SpawnableBase {

	@Override
	public List<Entity> prepareFromMap(int levelID, MapObject mapObj) {
		return null;
	}

	@Override
	public Entity prepareFromData(int levelID, Object... objects) {
		Entity ent = EntityFactory.createEntity_NPC(levelID, (Float)objects[0], (Float)objects[1]);
		ent.getComponent(EntityData.class).setTeam((int) objects[2]);
		return ent;
	}
	
}
