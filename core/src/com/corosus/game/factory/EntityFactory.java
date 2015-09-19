package com.corosus.game.factory;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.Velocity;
import com.corosus.game.entity.EnumEntityType;

public class EntityFactory {

	public static Entity createEntity(EnumEntityType type, float posX, float posY) {
		return createEntity(type, posX, posY, 0, 0);
	}
	
	public static Entity createEntity(EnumEntityType type, float posX, float posY, float motionX, float motionY) {
		EntityBuilder ent = new EntityBuilder(Game_AI_TestBed.instance().getWorld())
		.with(new Position(posX, posY))
		.with(new Velocity(motionX, motionY))
		.with(new EntityData(type));
		
		//ent
		if (type == EnumEntityType.SPRITE) {
			ent.with(new Health(100));
			ent.with(new RenderData("tanya"));
		//projectile
		} else if (type == EnumEntityType.PROJECTILE) {
			ent.with(new Health(1));
			ent.with(new RenderData("civ1"));
		}
		
		Game_AI_TestBed.instance().entityCount++;
		
		return ent.build();
	}
	
}
