package com.corosus.game.factory;

import java.util.HashMap;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Health;
import com.corosus.game.component.PhysicsData;
import com.corosus.game.component.PlayerData;
import com.corosus.game.component.Position;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.ProjectileData;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.Velocity;
import com.corosus.game.component.WeaponData;
import com.corosus.game.entity.ActionRoutineDodge;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.factory.spawnable.SpawnableBase;
import com.corosus.game.factory.spawnable.SpawnableGeneral;
import com.corosus.game.factory.spawnable.SpawnableSoldier;
import com.corosus.game.factory.spawnable.SpawnablePlayer;
import com.corosus.game.factory.spawnable.SpawnablePrjPulse;
import com.corosus.game.factory.spawnable.SpawnableTypes;

public class EntityFactory {
	
	/**
	 * primary purpose of this design is for deserialization of map data
	 * also to be used as final implementation of factory based spawning of entities
	 */
	private static HashMap<String, SpawnableBase> lookupSpawnables = new HashMap<String, SpawnableBase>();

	static {
		addEntity(SpawnableTypes.SPRITE_PLAYER, new SpawnablePlayer()); //TODO: USE THIS
		addEntity(SpawnableTypes.SPRITE_SOLDIER, new SpawnableSoldier());
		addEntity(SpawnableTypes.SPRITE_GENERAL, new SpawnableGeneral());
		addEntity(SpawnableTypes.PRJ_PULSE, new SpawnablePrjPulse());
	}
	
	public static void addEntity(String name, SpawnableBase base) {
		lookupSpawnables.put(name, base);
	}
	
	public static SpawnableBase getEntity(String name) {
		return lookupSpawnables.get(name);
	}
	
	
	/*public static Entity createEntity_Mission(String type, float posX, float posY) {
		Entity ent = createEntity_NPC(posX, posY);
		
		
		
		return ent;
	}*/
	
	public static Entity createEntity_Player(float posX, float posY) {
		EntityBuilder ent = getTemplate_Common(posX, posY);
		
		EntityData data = new EntityData(EnumEntityType.SPRITE);
		data.aiControlled = false;
		data.inputControlled = true;
		data.setTeam(EntityData.TEAM_PLAYER);
		data.sizeDiameter = Cst.COLLIDESIZE_SPRITE;
		
		ProfileData profile = new ProfileData();
		profile.addAction(new ActionRoutineDodge(3));
		
		RenderData render = new RenderData("player");
		
		ent
		.with(new PlayerData())
		.with(data)
		.with(new WeaponData())
		.with(profile)
		.with(render)
		;
		
		Game_AI_TestBed.instance().getLevel().addToEntityCount(1);
		
		return ent.build();
	}
	
	public static Entity createEntity_NPC(float posX, float posY) {
		EntityBuilder ent = getTemplate_Common(posX, posY);
		
		EntityData data = new EntityData(EnumEntityType.SPRITE);
		data.aiControlled = true;
		data.sizeDiameter = Cst.COLLIDESIZE_SPRITE;
		
		ProfileData profile = new ProfileData();
		
		ent
		.with(new Health(100))
		.with(new RenderData())
		.with(data)
		.with(new WeaponData())
		.with(profile)
		;
		
		Game_AI_TestBed.instance().getLevel().addToEntityCount(1);
		
		return ent.build();
	}
	
	public static Entity createEntity_Projectile(float posX, float posY) {
		EntityBuilder ent = getTemplate_Common(posX, posY);
		
		EntityData data = new EntityData(EnumEntityType.PROJECTILE);
		data.aiControlled = true;
		data.sizeDiameter = Cst.COLLIDESIZE_PROJECTILE;
		
		ProfileData profile = new ProfileData();
		
		ent
		.with(new Health(1))
		.with(new RenderData("prj_energyblast"))
		.with(data)
		.with(profile)
		.with(new ProjectileData())
		;
		
		Game_AI_TestBed.instance().getLevel().addToEntityCount(1);
		
		return ent.build();
	}
	
	public static EntityBuilder getTemplate_Common(float posX, float posY) {
		EntityBuilder ent = new EntityBuilder(Game_AI_TestBed.instance().getLevel().getWorld())
		.with(new Position(posX, posY))
		.with(new Velocity())
		.with(new PhysicsData())
		.with(new Health(100))
		.with(new RenderData())
		;
		return ent;
	}
	
	

	/*public static Entity createEntity(EnumEntityType type, float posX, float posY) {
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
			ent.with(new RenderData("prj_energyblast"));
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
		
		ProfileData profile = new ProfileData();
		profile.addAction(new ActionRoutineDodge(3));
		
		EntityBuilder ent = new EntityBuilder(Game_AI_TestBed.instance().getLevel().getWorld())
		.with(new Position(posX, posY))
		.with(new Velocity(motionX, motionY))
		.with(data)
		.with(profile)
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
	}*/
	
}
