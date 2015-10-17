package com.corosus.game.factory.spawnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.artemis.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.corosus.game.component.EntityData;
import com.corosus.game.factory.EntityFactory;

public class SpawnableBaseNPC implements SpawnableBase {

	@Override
	public List<Entity> prepareFromMap(int levelID, MapObject mapObj) {

		MapProperties props = mapObj.getProperties();
		
		float x = (float) props.get("x");
		float y = (float) props.get("y");
		
		//TODO: use this for area spawning
		float width = (float) props.get("width");
		float height = (float) props.get("height");
		
		Random rand = new Random();
		
		//default to main enemy team if no data
		int team = EntityData.TEAM_1;
		if (props.containsKey("team")) {
			team = (int) props.get("team");
		}
		
		int count = 1;
		
		if (props.containsKey("count")) {
			count = Integer.valueOf((String) props.get("count"));
		}
		
		List<Entity> listEnts = new ArrayList<>();
		
		for (int i = 0; i < count; i++) {
			listEnts.add(prepareFromData(levelID, x + rand.nextFloat() * width, y + rand.nextFloat() * height, team));
		}
		
		return listEnts;
	}

	@Override
	public Entity prepareFromData(int levelID, Object... objects) {
		Entity ent = EntityFactory.createEntity_NPC(levelID, (Float)objects[0], (Float)objects[1]);
		EntityData data = ent.getComponent(EntityData.class);
		data.setTeam((int) objects[2]);
		data.initAI(levelID, ent.getId());
		return ent;
	}
	
}
