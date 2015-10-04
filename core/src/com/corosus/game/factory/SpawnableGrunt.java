package com.corosus.game.factory;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

public class SpawnableGrunt implements SpawnableBase {

	@Override
	public void prepare(MapObject mapObj) {

		MapProperties props = mapObj.getProperties();
		
		float x = (float) props.get("x");
		float y = (float) props.get("y");
		
		float width = (float) props.get("width");
		float height = (float) props.get("height");
		
		int count = 1;
		
		if (props.containsKey("count")) {
			count = Integer.valueOf((String) props.get("count"));
		}
		
		for (int i = 0; i < count; i++) {
			Entity ent = EntityFactory.createEntity_NPC(x, y);
		}
		
		
		
		//ent.getComponent(Velocity.class).set(vecX, vecY)
	}
	
}
