package com.corosus.game.factory.spawnable;

import com.badlogic.gdx.maps.MapObject;

public interface SpawnableBase {

	public abstract void prepareFromMap(MapObject mapObj);
	
	public abstract void prepareFromData(Object... objects);
	
}
