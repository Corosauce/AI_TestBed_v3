package com.corosus.game.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Logger;
import com.corosus.game.client.input.XBox360Pad;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Position;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.Velocity;
import com.corosus.game.component.WeaponData;
import com.corosus.game.component.WeaponData.Weapon;
import com.corosus.game.factory.EntityFactory;
import com.corosus.game.factory.spawnable.SpawnableTypes;

public class GameInput extends IntervalEntityProcessingSystem {
	
	//keyboard
	public static HashMap<Integer, Boolean> lookupKeysDown = new HashMap<Integer, Boolean>();
	
	//mouse
	public static float mouseX = 0;
	public static float mouseY = 0;
	public static HashMap<Integer, Boolean> lookupMouseDown = new HashMap<Integer, Boolean>();
	
	//xbox controller
	public static Controller controller = null;
	
	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Velocity> mapVelocity;
	private ComponentMapper<ProfileData> mapProfile;
	private ComponentMapper<WeaponData> mapWeapons;
	
	public GameInput(float interval) {
		super(Aspect.exclude(), interval);
		
		/*lookupKeysDown.put(Input.Keys.W, false);
		lookupKeysDown.put(Input.Keys.A, false);
		lookupKeysDown.put(Input.Keys.S, false);
		lookupKeysDown.put(Input.Keys.D, false);
		lookupKeysDown.put(Input.Keys.SHIFT_LEFT, false);*/
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		//Logger.dbg("tick " + this);
		
		if (controller == null) {
			if (Controllers.getControllers().size > 0) {
				System.out.println("controllers found: " + Controllers.getControllers().size);
				controller = Controllers.getControllers().first();
			}
		} else {
			
		}
		
		Iterator<Entry<Integer, Boolean>> iter = lookupKeysDown.entrySet().iterator();
		
		Entity player = Game_AI_TestBed.instance().getLevel().getPlayerEntity();
		
		while (iter.hasNext()) {
			Map.Entry<Integer, Boolean> entry = iter.next();
			
			if (!entry.getValue()) continue;
			
			int keycode = entry.getKey();
			
			float speed = 15;
			
			if (keycode == Input.Keys.UP) {
				Game_AI_TestBed.instance().getCamera().translate(0, speed);
			}
			
			if (keycode == Input.Keys.DOWN) {
				Game_AI_TestBed.instance().getCamera().translate(0, -speed);
			}
			
			if (keycode == Input.Keys.LEFT) {
				Game_AI_TestBed.instance().getCamera().translate(-speed, 0);
			}
			
			if (keycode == Input.Keys.RIGHT) {
				Game_AI_TestBed.instance().getCamera().translate(speed, 0);
			}
			
			
			if (player != null) {
				
				
				
				
				
				
			}
			
			
			//if (entry.getKey() == )
		}
		
		if (player != null) {
			
			Velocity vel = mapVelocity.get(player);
			Position pos = mapPos.get(player);
			ProfileData profile = mapProfile.get(player);
			WeaponData weapons = mapWeapons.get(player);
			
			//force no movement unless input
			vel.x = 0F;
			vel.y = 0;
			
			//process controller
			if (controller != null) {
				System.out.println("data: " + controller.getAxis(XBox360Pad.AXIS_LEFT_X));
				
				float vecX = controller.getAxis(XBox360Pad.AXIS_LEFT_X);
				float vecY = -controller.getAxis(XBox360Pad.AXIS_LEFT_Y);
				
				float deadZone = 0.25F;
				
				if (vecX < -deadZone || vecX > deadZone) {
					vel.x = profile.moveSpeed * vecX;
				}
				if (vecY < -deadZone || vecY > deadZone) {
					vel.y = profile.moveSpeed * vecY;
				}
				
			} else {
				//System.out.println("cant find controller!");
			}
			
			try {

				boolean gridMovement = true;
				
				if (gridMovement) {
					if (keyDown(Input.Keys.A)) {
						vel.x += -profile.moveSpeed;
						//x = true;
					}
					
					if (keyDown(Input.Keys.D)) {
						vel.x += profile.moveSpeed;
						//if (x) vel.x = 0;
					}
					
					if (keyDown(Input.Keys.W)) {
						vel.y += profile.moveSpeed;
						//y = true;
					}
					
					if (keyDown(Input.Keys.S)) {
						vel.y += -profile.moveSpeed;
						//if (y) vel.y = 0;
					}
				} else {
					if (keyDown(Input.Keys.W)) {
						double rot = Math.toRadians(pos.rotationYaw);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					} 
					
					if (keyDown(Input.Keys.S)) {
						double rot = Math.toRadians(pos.rotationYaw + 180);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					}
					
					if (keyDown(Input.Keys.A)) {
						double rot = Math.toRadians(pos.rotationYaw + 90);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					} 
					
					if (keyDown(Input.Keys.D)) {
						double rot = Math.toRadians(pos.rotationYaw - 90);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					}
					
				}
				
				//dodge
				if (keyDown(Input.Keys.SHIFT_LEFT)) {
					
					float velX = vel.x;
					float velY = vel.y;
					
					double length = Math.sqrt(velX * velX + velY * velY);
					if (length > 0) {
						velX /= length;
						velY /= length;
					}
					
					float dodgeSpeed = profile.moveSpeed * 3;
					
					velX *= dodgeSpeed;
					velY *= dodgeSpeed;
					
					//try to activate the dodge which is currently added to index 0
					//TODO: assign a more sane action/skill system
					boolean result = profile.listRoutines.get(0).tryActivate(new Vector2(velX, velY));
					
					if (result) {
						Logger.dbg("dodged!");
					} else {
						Logger.dbg("cant dodge");
					}
				}
				
				
				//normalize speed
				double length = Math.sqrt(vel.x * vel.x + vel.y * vel.y);
				if (length > 0) {
					vel.x /= length;
					vel.y /= length;
				}
				
				vel.x *= profile.moveSpeed;
				vel.y *= profile.moveSpeed;
				
				//mouse shoot
				if (mouseDown(0)) {
					
					if (weapons.hasPrimaryWeapon()) {
						
						Weapon weapon = weapons.getActivePrimary();
						
						if (weapon.canFire()) {
							
							//TODO: group this stuff together better
							weapon.fire();
							double rot = Math.toRadians(pos.rotationYaw + 90);
							float vecX = (float) (Math.sin(rot))/* * profile.moveSpeed * 2F*/;
							float vecY = (float) (-Math.cos(rot))/* * profile.moveSpeed * 2F*/;
							EntityFactory.getEntity(SpawnableTypes.PRJ_PULSE).prepareFromData(pos.x + vecX, pos.y + vecY, EntityData.TEAM_PLAYER, vecX, vecY);
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//process keys
			iter = lookupKeysDown.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<Integer, Boolean> entry = iter.next();
				
				if (!entry.getValue()) continue;
				
				int keycode = entry.getKey();
				
				//boolean x = false;
				//boolean y = false;
				
				
				
				
			}
			
			//process mouse
			float screenSizeX = Gdx.graphics.getWidth();
			float screenSizeY = Gdx.graphics.getHeight();
			
			float cameraX = Game_AI_TestBed.instance().getCamera().position.x;
			float cameraY = Game_AI_TestBed.instance().getCamera().position.y;
			
			float mapCoordX = cameraX - (screenSizeX/2) + mouseX;
			float mapCoordY = cameraY - (screenSizeY/2) + (screenSizeY - mouseY);
			
			//System.out.println("screen: " + screenSizeX + " - " + screenSizeY);
			//System.out.println("camera: " + cameraX + " - " + cameraY);
			//System.out.println("mouse: " + mouseX + " - " + mouseY);
			
			/*pos.x = mapCoordX;
			pos.y = mapCoordY;*/
			
			/*vel.x = -(pos.x - mapCoordX);
			vel.y = -(pos.y - mapCoordY);*/
			
			//make rotationYaw be aimed at mouse
			float vecX = -(pos.x - mapCoordX);
			float vecY = -(pos.y - mapCoordY);
			
			double angle = Math.toDegrees(Math.atan2(vecY, vecX));
			if (angle < 0) angle += 360;
			
			pos.rotationYaw = (float) angle;
			
			//System.out.println(angle);
			
			//Orient.fromAngle(angle);
		}
		
		//super.processSystem();
	}
	
	public boolean keyDown(int keyCode) {
		if (!lookupKeysDown.containsKey(keyCode)) return false;
		return lookupKeysDown.get(keyCode);
	}
	
	public boolean mouseDown(int buttonCode) {
		if (!lookupMouseDown.containsKey(buttonCode)) return false;
		return lookupMouseDown.get(buttonCode);
	}

}
