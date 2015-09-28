package com.corosus.game.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2f;

import net.mostlyoriginal.game.util.VecUtil;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Logger;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Health;
import com.corosus.game.component.PhysicsData;
import com.corosus.game.component.Position;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.Velocity;
import com.corosus.game.entity.ActionRoutine;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.factory.EntityFactory;

@Wire
public class SpriteSimulate extends IntervalEntityProcessingSystem {

	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Health> mapHealth;
	private ComponentMapper<Velocity> mapVelocity;
	private ComponentMapper<EntityData> mapData;
	private ComponentMapper<ProfileData> mapProfile;
	private ComponentMapper<PhysicsData> mapPhysics;
	
	private List<CollisionEntry> listCollisionQueueStart = new ArrayList<CollisionEntry>();
	private List<CollisionEntry> listCollisionQueueEnd = new ArrayList<CollisionEntry>();
	
	public class CollisionEntry {
		
		public int entIDA;
		public int entIDB;
		
		public CollisionEntry(int entIDA, int entIDB) {
			this.entIDA = entIDA;
			this.entIDB = entIDB;
		}
	}
	
	public SpriteSimulate(float interval) {
		super(Aspect.all(Position.class, Health.class, Velocity.class, EntityData.class, ProfileData.class, PhysicsData.class), interval);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		
	}

	@Override
	protected void process(Entity e) {
		
		//Position pos = mapPos.get(e);
		Health health = mapHealth.get(e);
		Position pos = mapPos.get(e);
		Velocity motion = mapVelocity.get(e);
		EntityData data = mapData.get(e);
		ProfileData profileData = mapProfile.get(e);
		PhysicsData physics = mapPhysics.get(e);
		
		if (physics.needInit) {
			physics.needInit = false;
			
			short categoryBits = 0;
			if (data.type == EnumEntityType.SPRITE) {
				categoryBits = PhysicsData.COLLIDE_SPRITE;
			} else if (data.type == EnumEntityType.PROJECTILE) {
				categoryBits = PhysicsData.COLLIDE_PROJECTILE;
			}
			
			short maskBits = 0;
			if (data.type == EnumEntityType.SPRITE) {
				maskBits = (short) (PhysicsData.COLLIDE_SPRITE | PhysicsData.COLLIDE_PROJECTILE);
			} else if (data.type == EnumEntityType.PROJECTILE) {
				maskBits = PhysicsData.COLLIDE_SPRITE;
			}
			
			physics.initPhysics(e.id, pos.x, pos.y, categoryBits, maskBits);
		}
		
		List<Component> listComponents = new ArrayList<Component>();
		listComponents.add(motion);
		
		for (int i = 0; i < profileData.listRoutines.size(); i++) {
			ActionRoutine action = profileData.listRoutines.get(i);
			action.tick(listComponents);
			if (action.isActive()) {
				
			}/* else {
				action.dispose();
				profileData.listRoutines.remove(i--);
			}*/
		}
		
		Random rand = new Random();
		
		if (data.type == EnumEntityType.SPRITE) {
			if (data.aiControlled) {
				
				//TEMP DUMMY AI
				profileData.moveSpeed = 5;
				
				/*motion.x = rand.nextInt(2)-rand.nextInt(2);
				motion.y = rand.nextInt(2)-rand.nextInt(2);*/
				
				Entity player = Game_AI_TestBed.instance().getLevel().getPlayerEntity();
				
				if (player != null) {
					
					Position posPlayer = mapPos.get(player);
					
					float dist = VecUtil.getDist(new Vector2f(pos.x, pos.y), new Vector2f(posPlayer.x, posPlayer.y));
					
					if (dist < 400) {
						Vector2f targVec = VecUtil.getTargetVector(pos.x, pos.y, posPlayer.x, posPlayer.y);
						
						motion.x = targVec.x * profileData.moveSpeed;
						motion.y = targVec.y * profileData.moveSpeed;
						
						//make rotationYaw be aimed at motion
						double angle = Math.toDegrees(Math.atan2(motion.y, motion.x));
						if (angle < 0) angle += 360;
						
						pos.rotationYaw = (float) angle;
						
						if (Game_AI_TestBed.instance().getLevel().getGameTime() % 2 == 0) {
							//for (int i = 0; i < 1; i++) {
							if (rand.nextInt(5) == 0) {
								//System.out.println("spawn");
								float speed = profileData.moveSpeed * 4F;
								//float vecX = rand.nextFloat() * speed - rand.nextFloat() * speed;
								//float vecY = rand.nextFloat() * speed - rand.nextFloat() * speed;
								
								//Vector2 targVec = VecUtil.getTargetVector(pos.x, pos.y, posPlayer.x, posPlayer.y);
								
								float vecX = targVec.x * speed;
								float vecY = targVec.y * speed;
								EntityFactory.createEntity(EnumEntityType.PROJECTILE, pos.x + vecX * 2, pos.y + vecY * 2, vecX, vecY);
							}
						}
					}
				}
				
				//health.hp--;
				
				//
				
				
				
			} else {
				
			}
			
			/*float drag = 0.15F;
			
			motion.x *= drag;
			motion.y *= drag;*/
		} else if (data.type == EnumEntityType.PROJECTILE) {
			
			if (health.lifeTime > 100) {
				
				//TODO: RELOCATE TO PROPER CLEANUP METHOD
				killEntity(physics, e);
			}
		}
		
		//physics
		pos.prevX = pos.x;
		pos.prevY = pos.y;
		
		pos.x += motion.x;
		pos.y += motion.y;
		
		health.lifeTime++;
		
		//possibly relocate this code to physicswrapper system
		if (physics.body != null) {
			physics.body.setTransform(pos.x, pos.y, 0);
			physics.body.applyForceToCenter(0, 0, true);
		}
		
		if (pos.x < 0) {
			pos.setPos(0, pos.y);
		}
		
		if (pos.x > Game_AI_TestBed.instance().getLevel().getLevelSizeX()) {
			pos.setPos(Game_AI_TestBed.instance().getLevel().getLevelSizeX(), pos.y);
		}
		
		if (pos.y < 0) {
			pos.setPos(pos.x, 0);
		}
		
		if (pos.y > Game_AI_TestBed.instance().getLevel().getLevelSizeY()) {
			pos.setPos(pos.x, Game_AI_TestBed.instance().getLevel().getLevelSizeY());
		}
		
		/*if (pos.x < 0 || pos.x > Game_AI_TestBed.instance().getLevel().getLevelSizeX() || pos.y < 0 || pos.y > Game_AI_TestBed.instance().getLevel().getLevelSizeY()) {
			//System.out.println("killed out of bound entity");
			Game_AI_TestBed.instance().getLevel().killEntity(e);
		}*/
		
		if (health.isDead()) {
			//System.out.println("killed entity");
			killEntity(physics, e);
		}
	}
	
	public void killEntity(PhysicsData phys, Entity e) {
		phys.dispose();
		Game_AI_TestBed.instance().getLevel().killEntity(e);
	}

	@Override
	protected void processSystem() {
		super.processSystem();
		
		for (CollisionEntry entry : listCollisionQueueStart) {
			//Logger.dbg("collision start between " + entry.entIDA + " and " + entry.entIDB);
			
			Entity entIDA = Game_AI_TestBed.instance().getLevel().getWorld().getEntity(entry.entIDA);
			if (entIDA != null && entIDA != Game_AI_TestBed.instance().getLevel().getPlayerEntity()) {
				Health health = mapHealth.get(entIDA);
				health.hp = 0;
			}
			
			Entity entIDB = Game_AI_TestBed.instance().getLevel().getWorld().getEntity(entry.entIDB);
			if (entIDB != null && entIDB != Game_AI_TestBed.instance().getLevel().getPlayerEntity()) {
				Health health = mapHealth.get(entIDB);
				health.hp = 0;
			}
		}
		listCollisionQueueStart.clear();
		
		for (CollisionEntry entry : listCollisionQueueEnd) {
			//Logger.dbg("collision end between " + entry.entIDA + " and " + entry.entIDB);
		}
		listCollisionQueueEnd.clear();
	}

	public List<CollisionEntry> getListCollisionQueueStart() {
		return listCollisionQueueStart;
	}
	
	public void triggerCollisionEvent(int entIDA, int entIDB) {
		listCollisionQueueStart.add(new CollisionEntry(entIDA, entIDB));
	}
	
	public void triggerCollisionEndEvent(int entIDA, int entIDB) {
		listCollisionQueueEnd.add(new CollisionEntry(entIDA, entIDB));
	}
	
}
