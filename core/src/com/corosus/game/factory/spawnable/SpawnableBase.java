package com.corosus.game.factory.spawnable;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;

public interface SpawnableBase {

	public abstract List<Entity> prepareFromMap(int levelID, MapObject mapObj);
	
	public abstract Entity prepareFromData(int levelID, Object... objects);
	
}
