package com.corosus.game.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.corosus.game.Cst;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.Logger;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Health;
import com.corosus.game.component.PhysicsData;
import com.corosus.game.component.Position;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.ProjectileData;
import com.corosus.game.component.Velocity;
import com.corosus.game.component.WeaponData;
import com.corosus.game.component.WeaponData.Weapon;
import com.corosus.game.component.WeaponData.WeaponLocation;
import com.corosus.game.entity.ActionRoutine;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.factory.EntityFactory;
import com.corosus.game.factory.spawnable.SpawnableTypes;
import com.corosus.game.util.MathUtil;
import com.corosus.game.util.VecUtil;

public class SpriteSim extends IntervalEntityProcessingSystem {

	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Health> mapHealth;
	private ComponentMapper<Velocity> mapVelocity;
	private ComponentMapper<EntityData> mapData;
	private ComponentMapper<ProfileData> mapProfile;
	private ComponentMapper<PhysicsData> mapPhysics;
	private ComponentMapper<ProjectileData> mapProjectile;
	private ComponentMapper<WeaponData> mapWeapons;
	
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
	
	public SpriteSim(float interval) {
		super(Aspect.one(Position.class, Health.class, Velocity.class, EntityData.class, ProfileData.class, PhysicsData.class, ProjectileData.class), interval);
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
		//ProjectileData projData = mapProjectile.get(e);
		
		if (physics.needInit) {
			physics.needInit = false;
			
			//need to make enemy projectiles not able to hurt enemies
			//teams?
			
			short categoryBits = 0;
			if (data.type == EnumEntityType.SPRITE) {
				if (data.team == 0) {
					categoryBits = PhysicsData.COLLIDE_TEAM0_SPRITE;
				} else if (data.team == 1) {
					categoryBits = PhysicsData.COLLIDE_TEAM1_SPRITE;
				} else {
					Logger.dbg("MISSING TEAM COLLIDE DATA");
				}
				
			} else if (data.type == EnumEntityType.PROJECTILE) {
				if (data.team == 0) {
					categoryBits = PhysicsData.COLLIDE_TEAM0_PROJECTILE;
				} else if (data.team == 1) {
					categoryBits = PhysicsData.COLLIDE_TEAM1_SPRITE;
				} else {
					Logger.dbg("MISSING TEAM COLLIDE DATA");
				}
			}
			
			short maskBits = 0;
			if (data.type == EnumEntityType.SPRITE) {
				if (data.team == 0) {
					maskBits = PhysicsData.COLLIDE_TEAM1_PROJECTILE;
				} else if (data.team == 1) {
					maskBits = PhysicsData.COLLIDE_TEAM0_PROJECTILE;
				} else {
					Logger.dbg("MISSING TEAM COLLIDE DATA");
				}
			} else if (data.type == EnumEntityType.PROJECTILE) {
				if (data.team == 0) {
					maskBits = PhysicsData.COLLIDE_TEAM1_SPRITE;
					
					//make projectiles also collide with eachother
					//maskBits = (short) (PhysicsData.COLLIDE_TEAM1_SPRITE | PhysicsData.COLLIDE_TEAM1_PROJECTILE);
				} else if (data.team == 1) {
					maskBits = PhysicsData.COLLIDE_TEAM0_SPRITE;
				} else {
					Logger.dbg("MISSING TEAM COLLIDE DATA");
				}
			}
			
			//TODO: refine use of CCD on fast moving projectiles only, determine best based on smallest sprite and prj size / speed
			physics.initPhysics(e.getId(), pos.x, pos.y, data.sizeDiameter, categoryBits, maskBits, data.type == EnumEntityType.PROJECTILE);
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
		
		//Logger.dbg("data.type: " + data.type + " pos: " + pos.x + ", " + pos.y);
		
		if (data.type == EnumEntityType.SPRITE) {
			
			WeaponData weapons = mapWeapons.get(e);
			
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
						if (VecUtil.canSee(pos.toVec(), posPlayer.toVec())) {
							Vector2f targVec = VecUtil.getTargetVector(pos.x, pos.y, posPlayer.x, posPlayer.y);
							
							if (dist > 64) {
								motion.x = targVec.x * profileData.moveSpeed;
								motion.y = targVec.y * profileData.moveSpeed;
							} else {
								motion.x = 0;
								motion.y = 0;
							}
							
							//make rotationYaw be aimed at motion
							double angle = Math.toDegrees(Math.atan2(motion.y, motion.x));
							if (angle < 0) angle += 360;
							
							pos.rotationYaw = (float) angle;
							
							if (weapons.hasPrimaryWeapon()) {
								Weapon weapon = weapons.getActivePrimary();
								
								if (weapon.canFire()) {
									weapon.fire();
									GameAssetManager.instance().getSound("shoot").play(GameSettings.vol);
									
									//float speed = proj.moveSpeed * 4F;
									float vecX = targVec.x;
									float vecY = targVec.y;
									
									EntityFactory.getEntity(weapon.projectileType).prepareFromData(pos.x + vecX * 2, pos.y + vecY * 2, data.team, vecX, vecY);
								}
							}
							
							/*if (Game_AI_TestBed.instance().getLevel().getGameTime() % 2 == 0) {
								if (rand.nextInt(5) == 0) {
									float speed = profileData.moveSpeed * 4F;
									
									float vecX = targVec.x * speed;
									float vecY = targVec.y * speed;
									EntityFactory.getEntity(SpawnableTypes.PRJ_PULSE).prepareFromData(pos.x + vecX * 2, pos.y + vecY * 2, data.team, vecX, vecY);
								}
							}*/
						}
					} else {
						int speed = 10;
						motion.x = rand.nextInt(speed)-rand.nextInt(speed);
						motion.y = rand.nextInt(speed)-rand.nextInt(speed);
					}
				}
				
				//health.hp--;
				
				//
				
				
			} else {
				
			}
			
			//TODO: is this proper ecs design? maybe we should have a subsystem for weapon logic? or just a separate system?
			
			//process weapons - cooldowns all weapons
			for (WeaponLocation weapLoc : weapons.listWeaponLocations) {
				for (Weapon weapon : weapLoc.listWeapons) {
					if (weapon.ticksCooldownCur > 0) {
						weapon.ticksCooldownCur--;
					}
				}
			}
			
			/*float drag = 0.15F;
			
			motion.x *= drag;
			motion.y *= drag;*/
		} else if (data.type == EnumEntityType.PROJECTILE) {
			
			if (health.lifeTime > 100) {
				
				//TODO: RELOCATE TO PROPER CLEANUP METHOD
				killEntity(e);
			}
		}
		
		//physics
		pos.prevX = pos.x;
		pos.prevY = pos.y;
		
		Level level = Game_AI_TestBed.instance().getLevel();
		
		int fPosX = (int) (pos.x + motion.x);
		int fPosY = (int) (pos.y + motion.y);
		int fTileX = MathUtil.floorF((float)fPosX / (float)Cst.TILESIZE);
		int fTileY = MathUtil.floorF((float)fPosY / (float)Cst.TILESIZE);
		
		Vector4f vec = Game_AI_TestBed.instance().getLevel().getCellBorder(fPosX, (int) pos.y);
		//Logger.dbg("x: " + fPosX + " - y: " + fPosY + " vs " + vec + " passable: " + level.isPassable(fPosX, fPosY));
		//Logger.dbg("real pos: " + fPosX + " - " + fPosY + " tile pos: " + fTileX + " - " + fTileY);
		
		boolean collide = false;
		boolean bounce = false;
		
		if (!level.isPassable(fPosX, (int) pos.y) && motion.x < 0 && fPosX < vec.x + Cst.TILESIZE) {
			//System.out.println("adjust -x!");
			fPosX = (int) (vec.x + Cst.TILESIZE) + 1;
			if (bounce) {
				motion.x *= -1;
			} else {
				motion.x = 0;
			}
			collide = true;
		}
		
		vec = Game_AI_TestBed.instance().getLevel().getCellBorder(fPosX, (int) pos.y);
		if (!level.isPassable(fPosX, (int) pos.y) && motion.x > 0 && fPosX > vec.x) {
			//System.out.println("adjust +x!");
			fPosX = (int) (vec.x) - 1;
			if (!collide) {
				if (bounce) {
					motion.x *= -1;
				} else {
					motion.x = 0;
				}
			}
			collide = true;
		}
		
		vec = Game_AI_TestBed.instance().getLevel().getCellBorder((int) pos.x, fPosY);
		if (!level.isPassable((int) pos.x, fPosY) && motion.y < 0 && fPosY < vec.y + Cst.TILESIZE) {
			//System.out.println("adjust -y!");
			fPosY = (int) (vec.y + Cst.TILESIZE) + 1;
			if (!collide) {
				if (bounce) {
					motion.y *= -1;
				} else {
					motion.y = 0;
				}
			}
			collide = true;
		}
		
		vec = Game_AI_TestBed.instance().getLevel().getCellBorder((int) pos.x, fPosY);
		if (!level.isPassable((int) pos.x, fPosY) && motion.y > 0 && fPosY > vec.y) {
			//System.out.println("adjust +y!");
			fPosY = (int) (vec.y) - 1;
			if (!collide) {
				if (bounce) {
					motion.y *= -1;
				} else {
					motion.y = 0;
				}
			}
			collide = true;
		}
			
			//left
			/*if (fPosX < vec.x + Cst.TILESIZE) {
				System.out.println("adjust!");
				fPosX = (int) (vec.x + Cst.TILESIZE) + 1;
			} else if (fPosX > vec.x) {
				System.out.println("adjust2!");
				fPosX = (int) (vec.x) - 1;
			}*/
			
		
		
		vec = Game_AI_TestBed.instance().getLevel().getCellBorder(fPosX, fPosY);
		
		if (!level.isPassable(fPosX, fPosY)) {
			
			//up
			/*if (fPosY > vec.y + Cst.TILESIZE) {
				System.out.println("adjust!");
				fPosY = (int) (vec.y + Cst.TILESIZE);
			}*/
			
		}
		
		
		pos.x = fPosX;
		pos.y = fPosY;
		
		health.lifeTime++;
		
		//possibly relocate this code to physicswrapper system
		if (physics.body != null) {
			physics.body.setTransform(pos.x, pos.y, 0);
			physics.body.applyForceToCenter(0, 0, true);
		}
		
		/*if (pos.x < 0) {
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
		}*/
		
		/*if (pos.x < 0 || pos.x > Game_AI_TestBed.instance().getLevel().getLevelSizeX() || pos.y < 0 || pos.y > Game_AI_TestBed.instance().getLevel().getLevelSizeY()) {
			//System.out.println("killed out of bound entity");
			Game_AI_TestBed.instance().getLevel().killEntity(e);
		}*/
		
		if (health.isDead()) {
			//System.out.println("killed entity");
			killEntity(e);
		}
	}
	
	public void killEntity(Entity e) {
		if (e.getId() == Game_AI_TestBed.instance().getLevel().getPlayerEntity().getId()) {
			Game_AI_TestBed.instance().getLevel().respawnPlayer();
		} else {
			PhysicsData phys = mapPhysics.get(e);
			phys.dispose();
			Game_AI_TestBed.instance().getLevel().killEntity(e);
		}
		
	}

	@Override
	protected void processSystem() {
		super.processSystem();
		
		for (CollisionEntry entry : listCollisionQueueStart) {
			Logger.dbg("collision start between " + entry.entIDA + " and " + entry.entIDB);
			
			Entity entIDA = Game_AI_TestBed.instance().getLevel().getWorld().getEntity(entry.entIDA);
			Entity entIDB = Game_AI_TestBed.instance().getLevel().getWorld().getEntity(entry.entIDB);
			
			//needed?
			if (entIDA == null || entIDB == null) continue;
			
			EntityData dataA = mapData.get(entIDA);
			EntityData dataB = mapData.get(entIDB);
			
			Health healthA = mapHealth.get(entIDA);
			Health healthB = mapHealth.get(entIDB);
			
			//Entity projectile = null;
			//Entity other = null;
			
			if (entIDA != null && dataA.type == EnumEntityType.PROJECTILE) {
				ProjectileData projDataA = mapProjectile.get(entIDA);
				healthB.hp -= projDataA.prjDamage;
				healthA.hp = 0;
				GameAssetManager.instance().getSound("hit").play(GameSettings.vol);
			} else if (entIDB != null && dataB.type == EnumEntityType.PROJECTILE) {
				ProjectileData projDataB = mapProjectile.get(entIDB);
				healthA.hp -= projDataB.prjDamage;
				healthB.hp = 0;
				GameAssetManager.instance().getSound("hit").play(GameSettings.vol);
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
