package com.corosus.game.factory;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.PhysicsData;
import com.corosus.game.component.PlayerData;
import com.corosus.game.component.Position;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.Velocity;
import com.corosus.game.entity.EnumEntityType;

public class EntityFactory {

	public static Entity createEntity(EnumEntityType type, float posX, float posY) {
		return createEntity(type, posX, posY, 0, 0);
	}
	
	public static Entity createEntity(EnumEntityType type, float posX, float posY, float motionX, float motionY) {
		
		EntityData data = new EntityData(type);
		data.aiControlled = true;
		
		EntityBuilder ent = getEntityTemplate_Common(data, posX, posY, motionX, motionY);
		
		//ent
		if (type == EnumEntityType.SPRITE) {
			ent.with(new Health(100));
			ent.with(new RenderData("tanya"));
		//projectile
		} else if (type == EnumEntityType.PROJECTILE) {
			ent.with(new Health(1));
			ent.with(new RenderData("civ1"));
		}
		
		Game_AI_TestBed.instance().getLevel().addToEntityCount(1);
		
		return ent.build();
	}
	
	public static Entity createPlayer(float posX, float posY) {
		
		EntityData data = new EntityData(EnumEntityType.SPRITE);
		data.aiControlled = false;
		data.inputControlled = true;
		
		EntityBuilder ent = getEntityTemplate_Common(data, posX, posY, 0, 0);
		
		ent.with(new PlayerData());
		
		ent.with(new Health(100));
		ent.with(new RenderData("civ1"));
		
		Game_AI_TestBed.instance().getLevel().addToEntityCount(1);
		
		return ent.build();
	}
	
	public static EntityBuilder getEntityTemplate_Common(EntityData data, float posX, float posY, float motionX, float motionY) {
		EntityBuilder ent = new EntityBuilder(Game_AI_TestBed.instance().getLevel().getWorld())
		.with(new Position(posX, posY))
		.with(new Velocity(motionX, motionY))
		.with(data)
		.with(new ProfileData())
		.with(new PhysicsData())
		;
		return ent;
	}
	
	public static EntityBuilder getEntityTemplate_Entity_AI(EnumEntityType type, float posX, float posY, float motionX, float motionY) {
		return null;
	}
	
	public static EntityBuilder getEntityTemplate_Entity_Projectile(EnumEntityType type, float posX, float posY, float motionX, float motionY) {
		return null;
	}
	
	public static EntityBuilder getEntityTemplate_Entity_Player(EnumEntityType type, float posX, float posY, float motionX, float motionY) {
		return null;
	}
	
}
