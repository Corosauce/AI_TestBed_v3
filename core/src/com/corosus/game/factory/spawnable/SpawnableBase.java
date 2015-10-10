package com.corosus.game.factory.spawnable;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;

public interface SpawnableBase {

	public abstract List<Entity> prepareFromMap(MapObject mapObj);
	
	public abstract Entity prepareFromData(Object... objects);
	
}
