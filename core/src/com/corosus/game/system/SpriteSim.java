package com.corosus.game.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
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

	public class CollisionEntry {

		public int entIDA;
		public int entIDB;

		public CollisionEntry(int entIDA, int entIDB) {
			this.entIDA = entIDA;
			this.entIDB = entIDB;
		}
	}

	public SpriteSim(float interval) {
		super(Aspect.one(Position.class, Health.class, Velocity.class,
				EntityData.class, ProfileData.class, PhysicsData.class,
				ProjectileData.class), interval);
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
		
		Level level = Game_AI_TestBed.instance().getLevel(data.levelID);
		
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
					maskBits = (short) (PhysicsData.COLLIDE_TEAM1_PROJECTILE | PhysicsData.COLLIDE_TEAM0_SPRITE | PhysicsData.COLLIDE_TEAM1_SPRITE);
				} else if (data.team == 1) {
					maskBits = (short) (PhysicsData.COLLIDE_TEAM0_PROJECTILE | PhysicsData.COLLIDE_TEAM1_SPRITE | PhysicsData.COLLIDE_TEAM0_SPRITE);
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
			physics.initPhysics(data.levelID, e.getId(), pos.x, pos.y, data.sizeDiameter, categoryBits, maskBits, data.type == EnumEntityType.PROJECTILE);
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
				
				data.getAgent().tick();
				
				/*if (weapons.hasPrimaryWeapon()) {
					Weapon weapon = weapons.getActivePrimary();
					float distRand = 1F;
					EntityFactory.getEntity(weapon.projectileType).prepareFromData(data.levelID, pos.x, pos.y, data.team, rand.nextFloat()-rand.nextFloat(), rand.nextFloat()-rand.nextFloat());
				}*/
				
				//TEMP DUMMY AI
				profileData.moveSpeed = 5;
				
				/*motion.x = rand.nextInt(2)-rand.nextInt(2);
				motion.y = rand.nextInt(2)-rand.nextInt(2);*/
				
				Entity player = level.getPlayerEntity();
				
				if (player != null) {
					
					Position posPlayer = mapPos.get(player);
					
					float distPlayer = VecUtil.getDist(new Vector2f(pos.x, pos.y), new Vector2f(posPlayer.x, posPlayer.y));
					
					boolean chase = false;
					boolean omnipotent = false;
					
					if (omnipotent || (distPlayer < 800 && VecUtil.canSee(data.levelID, pos.toVec(), posPlayer.toVec()))) {
						chase = true;
						data.getAgent().getBlackboard().setTargetID(player.getId());
					}
					
					
					
					if (data.getAgent().getBlackboard().getTargetID() != -1) {
						data.getAgent().moveTo(posPlayer.toVec());
					} else {
						int distRand = Cst.TILESIZE * 30;
						int tryCount = 10;
						if (rand.nextInt(30) == 0) {
							for (int i = 0; i < tryCount; i++) {
								Vector2f tryPos = new Vector2f(rand.nextInt(distRand)-rand.nextInt(distRand), rand.nextInt(distRand)-rand.nextInt(distRand));
								tryPos = new Vector2f(pos.x + tryPos.x, pos.y + tryPos.y);
								if (level.isPassable((int)tryPos.x, (int)tryPos.y)) {
									data.getAgent().moveTo(tryPos);
									break;
								}
							}
						}
					}
					
					if (chase) {
						
						Vector2f targVec = VecUtil.getTargetVector(pos.x, pos.y, posPlayer.x, posPlayer.y);
						
						
						/*if (distPlayer > 64) {
							motion.x = targVec.x * profileData.moveSpeed;
							motion.y = targVec.y * profileData.moveSpeed;
						} else {
							motion.x = 0;
							motion.y = 0;
						}*/
						
						if (weapons.hasPrimaryWeapon()) {
							Weapon weapon = weapons.getActivePrimary();
							
							if (weapon.canFire()) {
								//float speed = proj.moveSpeed * 4F;
								float vecX = targVec.x;
								float vecY = targVec.y;
								
								weapon.fire();
								GameAssetManager.instance().getSound("shoot").play(GameSettings.vol);
								EntityFactory.getEntity(weapon.projectileType).prepareFromData(data.levelID, pos.x + vecX * 2, pos.y + vecY * 2, data.team, vecX, vecY);
							}
						}
						
						/*if (level.getGameTime() % 2 == 0) {
							if (rand.nextInt(5) == 0) {
								float speed = profileData.moveSpeed * 4F;
								
								float vecX = targVec.x * speed;
								float vecY = targVec.y * speed;
								EntityFactory.getEntity(SpawnableTypes.PRJ_PULSE).prepareFromData(pos.x + vecX * 2, pos.y + vecY * 2, data.team, vecX, vecY);
							}
						}*/
					} else {
						
						/*motion.x = rand.nextInt(distRand)-rand.nextInt(distRand);
						motion.y = rand.nextInt(distRand)-rand.nextInt(distRand);*/
					}
				}
				
				//move to AI target position
				if (data.getAgent().getBlackboard().getPosTarget() != null) {
					float distToTarg = VecUtil.getDist(new Vector2f(pos.x, pos.y), data.getAgent().getBlackboard().getPosTarget());
					
					if (distToTarg > Cst.TILESIZE / 32) {
						Vector2f targVec = VecUtil.getTargetVector(new Vector2f(pos.x, pos.y), data.getAgent().getBlackboard().getPosTarget());
						
						motion.x = targVec.x * profileData.moveSpeed;
						motion.y = targVec.y * profileData.moveSpeed;
					}
				}
				
				//make rotationYaw be aimed at motion
				double angle = Math.toDegrees(Math.atan2(motion.y, motion.x));
				if (angle < 0) angle += 360;
				
				pos.rotationYaw = (float) angle;
				
				
			} else {
				//non AI player stuff...
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
				killEntity(data.levelID, e);
			}
		}
		
		//physics
		pos.prevX = pos.x;
		pos.prevY = pos.y;
		
		//process specific entity collision before applying motion to pos
		//List<CollisionEntry> listCollisions = lookupEntIDToCollisionList.get(e.getId());
		//if (listCollisions != null) {
		boolean spreadOutNPCs = true;
		if (spreadOutNPCs) {
			
			Vector2f pushVec = new Vector2f();

			
			float maxAxisSpeed = 3;
			float maxPushForce = 3;
			
			
			for (int entry : physics.listEntitiesCollidingWith) {
				int entIDTarget = entry;
				
				/*if (entry.entIDA == e.getId()) {
					entIDTarget = entry.entIDB;
				} else {
					entIDTarget = entry.entIDA;
				}*/
				
				Entity entHit = level.getWorld().getEntity(entIDTarget);
				
				if (entHit != null) {
					EntityData dataHit = mapData.get(entHit);
					Position posB = mapPos.get(entHit);
					if (dataHit != null && data.type == EnumEntityType.SPRITE) {
						if (dataHit.type == EnumEntityType.SPRITE) {
							
							
							
							Vector2f futurePos = new Vector2f(pos.x + motion.x, pos.y + motion.y);
							
							Vector2f pushA = VecUtil.getTargetVector(pos.toVec(), posB.toVec());
							//Vector2f pushB = VecUtil.getTargetVector(posB.toVec(), pos.toVec());
							
							float pushDistA = VecUtil.getDist(pos.toVec(), posB.toVec());
							//float pushDistB = VecUtil.getDist(posB.toVec(), pos.toVec());
							
							float cusionSize = Cst.COLLIDESIZE_SPRITE + 1;
							
							float pushForce = 8.5F / (cusionSize / (cusionSize - pushDistA));
							
							if (pushForce > maxPushForce) {
								pushForce = maxPushForce;
							}
							
							try {
								
								//player?
								if (data.getAgent() == null || dataHit.getAgent() == null) continue;
								
								//float distA = VecUtil.getDist(pos.toVec(), data.getAgent().getBlackboard().getPosTarget());
								//float distB = VecUtil.getDist(posB.toVec(), dataHit.getAgent().getBlackboard().getPosTarget());
								
								float distNow = VecUtil.getDist(pos.toVec(), posB.toVec());
								float distFuture = VecUtil.getDist(futurePos, posB.toVec());
								
								
								
								if (Float.isNaN(pushA.x)) {
									pushVec.x += rand.nextFloat() * pushForce;
								} else {
									pushVec.x += -pushA.x * pushForce;
								}
								
								if (Float.isNaN(pushA.y)) {
									pushVec.y += rand.nextFloat() * pushForce;
								} else {
									pushVec.y += -pushA.y * pushForce;
								}
								
								float speedReduce = (rand.nextFloat() * 0.5F) + 0.5F;
								speedReduce = (rand.nextFloat() * 1F);
								speedReduce = 0.001F;
								float speedIncrease = 0.8F;
								//speedReduce = 1F;
								
								//motion.x += pushVec.x;
								//motion.y += pushVec.y;
								
								//Logger.dbg("processing collision, distA: " + distA + ", distB: " + distB);
								
								/*if (e.getId() > entIDTarget && level.getPlayerEntity().getId() != e.getId()) {
									motion.x *= speedReduce;
									motion.y *= speedReduce;
									break;
								}*/
								
								
								
								if (distFuture < distNow) {
									/*motion.x *= speedReduce;
									motion.y *= speedReduce;
									break;*/
								} else {
									/*motion.x /= speedIncrease;
									motion.y /= speedIncrease;
									break;*/
								}
								
								/*if (distA < distB) {
									motion.x /= speedReduce;
									motion.y /= speedReduce;
									break;
								}  else if (distA > distB) {
									motion.x *= speedReduce;
									motion.y *= speedReduce;
									break;
								}*//* else if (e.getId() > entIDTarget && level.getPlayerEntity().getId() != e.getId()) {
									motion.x *= speedReduce;
									motion.y *= speedReduce;
									break;
								}*/
								
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						} else if (dataHit.type == EnumEntityType.PROJECTILE) {
							ProjectileData prjData = mapProjectile.get(entHit);
							Health prjHealth = mapHealth.get(entHit);
							
							health.hp -= prjData.prjDamage;
							//healthB.hp -= projDataA.prjDamage;
							prjHealth.hp = 0;
							GameAssetManager.instance().getSound("hit")
									.play(GameSettings.vol);
						}
					}
				}
				
			}
			
			if (pushVec.x > maxAxisSpeed) {
				pushVec.x = maxAxisSpeed;
			} else if (pushVec.x < -maxAxisSpeed) {
				pushVec.x = -maxAxisSpeed;
			}
			
			if (pushVec.y > maxAxisSpeed) {
				pushVec.y = maxAxisSpeed;
			} else if (pushVec.y < -maxAxisSpeed) {
				pushVec.y = -maxAxisSpeed;
			}
			
			if (pushVec != null) {
				motion.x += pushVec.x;
				motion.y += pushVec.y;
				//break;
			}
		}
		//}
		
		/*motion.x *= 0.98F;
		motion.y *= 0.98F;*/
		
		//temp code \\
		/*float maxAxisSpeed = 7;
		
		if (motion.x > maxAxisSpeed) {
			motion.x = maxAxisSpeed;
		} else if (motion.x < -maxAxisSpeed) {
			motion.x = -maxAxisSpeed;
		}
		
		if (motion.y > maxAxisSpeed) {
			motion.y = maxAxisSpeed;
		} else if (motion.y < -maxAxisSpeed) {
			motion.y = -maxAxisSpeed;
		}*/
		//temp code //
		
		int fPosX = (int) (pos.x + motion.x);
		int fPosY = (int) (pos.y + motion.y);
		//int fTileX = MathUtil.floorF((float)fPosX / (float)Cst.TILESIZE);
		//int fTileY = MathUtil.floorF((float)fPosY / (float)Cst.TILESIZE);
		
		Vector4f vec = level.getCellBorder(fPosX, (int) pos.y);
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
		
		vec = level.getCellBorder(fPosX, (int) pos.y);
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
		
		vec = level.getCellBorder((int) pos.x, fPosY);
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
		
		vec = level.getCellBorder((int) pos.x, fPosY);
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
			
		if (collide && data.type == EnumEntityType.PROJECTILE) {
			health.hp = 0;
		}
		
		vec = level.getCellBorder(fPosX, fPosY);
		
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
		
		if (pos.x > level.getLevelSizeX()) {
			pos.setPos(level.getLevelSizeX(), pos.y);
		}
		
		if (pos.y < 0) {
			pos.setPos(pos.x, 0);
		}
		
		if (pos.y > level.getLevelSizeY()) {
			pos.setPos(pos.x, level.getLevelSizeY());
		}*/
		
		/*if (pos.x < 0 || pos.x > level.getLevelSizeX() || pos.y < 0 || pos.y > level.getLevelSizeY()) {
			//System.out.println("killed out of bound entity");
			level.killEntity(e);
		}*/
		
		if (health.isDead()) {
			//System.out.println("killed entity");
			killEntity(data.levelID, e);
		}
	}

	public void killEntity(int levelID, Entity e) {
		Level level = Game_AI_TestBed.instance().getLevel(levelID);
		if (e.getId() == level.getPlayerEntity().getId()) {
			level.respawnPlayer();
		} else {
			PhysicsData phys = mapPhysics.get(e);
			// phys.dispose(levelID);
			PhysicsWrapper.pendRemoveBody(phys);
			level.killEntity(e);
		}

	}

	@Override
	protected void processSystem() {
		super.processSystem();
	}

	public void triggerCollisionEvent(int entIDA, int entIDB) {
		addToLookup(entIDA, entIDB);
		addToLookup(entIDB, entIDA);
	}

	public void addToLookup(int id, int idHit) {
		// TODO: WE ARE ASSUMING LEVEL 0 HERE, FIX!!!
		Level level = Game_AI_TestBed.instance().getLevel(0);
		Entity entA = level.getWorld().getEntity(id);
		Entity entB = level.getWorld().getEntity(idHit);
		
		if (entA != null && entB != null) {
			PhysicsData dataA = entA.getComponent(PhysicsData.class);
			PhysicsData dataB = entB.getComponent(PhysicsData.class);
			
			if (dataA != null) dataA.addCollision(idHit);
			if (dataB != null) dataB.addCollision(id);
		}
	}
	
	public void removeFromLookup(int id, int idHit) {
		// TODO: WE ARE ASSUMING LEVEL 0 HERE, FIX!!!
		Level level = Game_AI_TestBed.instance().getLevel(0);
		Entity entA = level.getWorld().getEntity(id);
		Entity entB = level.getWorld().getEntity(idHit);
		
		if (entA != null && entB != null) {
			PhysicsData dataA = entA.getComponent(PhysicsData.class);
			PhysicsData dataB = entB.getComponent(PhysicsData.class);
			
			if (dataA != null) dataA.removeCollision(idHit);
			if (dataB != null) dataB.removeCollision(id);
		}
	}

	public void triggerCollisionEndEvent(int entIDA, int entIDB) {
		removeFromLookup(entIDA, entIDB);
		removeFromLookup(entIDB, entIDA);
	}

}
