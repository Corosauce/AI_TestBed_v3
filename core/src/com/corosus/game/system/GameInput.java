package com.corosus.game.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.mostlyoriginal.api.component.basic.Pos;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.client.assets.Orient;
import com.corosus.game.component.Position;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.Velocity;
import com.corosus.game.input.XBox360Pad;

@Wire
public class GameInput extends IntervalEntityProcessingSystem {
	
	//keyboard
	public static HashMap<Integer, Boolean> lookupKeysDown = new HashMap<Integer, Boolean>();
	
	//mouse
	public static float mouseX = 0;
	public static float mouseY = 0;
	
	//xbox controller
	public static Controller controller = null;
	
	public GameInput(float interval) {
		super(Aspect.exclude(), interval);
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	protected void processSystem() {
		
		if (controller == null) {
			if (Controllers.getControllers().size > 0) {
				System.out.println("controllers found: " + Controllers.getControllers().size);
				controller = Controllers.getControllers().first();
			}
		} else {
			
		}
		
		ComponentMapper<Velocity> mapVelocity = ComponentMapper.getFor(Velocity.class, Game_AI_TestBed.instance().getLevel().getWorld());
		ComponentMapper<Position> mapPos = ComponentMapper.getFor(Position.class, Game_AI_TestBed.instance().getLevel().getWorld());
		ComponentMapper<ProfileData> mapProfile = ComponentMapper.getFor(ProfileData.class, Game_AI_TestBed.instance().getLevel().getWorld());
		
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
			
			//process keys
			iter = lookupKeysDown.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<Integer, Boolean> entry = iter.next();
				
				if (!entry.getValue()) continue;
				
				int keycode = entry.getKey();
				
				//boolean x = false;
				//boolean y = false;
				
				boolean gridMovement = false;
				
				if (gridMovement) {
					if (keycode == Input.Keys.A) {
						vel.x = -profile.moveSpeed;
						//x = true;
					}
					
					if (keycode == Input.Keys.D) {
						vel.x = profile.moveSpeed;
						//if (x) vel.x = 0;
					}
					
					if (keycode == Input.Keys.W) {
						vel.y = profile.moveSpeed;
						//y = true;
					}
					
					if (keycode == Input.Keys.S) {
						vel.y = -profile.moveSpeed;
						//if (y) vel.y = 0;
					}
				} else {
					if (keycode == Input.Keys.W) {
						double rot = Math.toRadians(pos.rotationYaw);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					} 
					
					if (keycode == Input.Keys.S) {
						double rot = Math.toRadians(pos.rotationYaw + 180);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					}
					
					if (keycode == Input.Keys.A) {
						double rot = Math.toRadians(pos.rotationYaw + 90);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					} 
					
					if (keycode == Input.Keys.D) {
						double rot = Math.toRadians(pos.rotationYaw - 90);
						vel.y += (float) (Math.sin(rot));
						vel.x += (float) (Math.cos(rot));
					}
					
					
					double length = Math.sqrt(vel.x * vel.x + vel.y * vel.y);
					vel.x /= length;
					vel.y /= length;
					
					vel.x *= profile.moveSpeed;
					vel.y *= profile.moveSpeed;
				}
				
				Game_AI_TestBed.instance().getCamera().position.x = pos.x;
				Game_AI_TestBed.instance().getCamera().position.y = pos.y;
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

}
