package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;

public class HUDRender extends IntervalEntityProcessingSystem {
	
	private ComponentMapper<Health> mapHealth;
	
	public HUDRender() {
		super(Aspect.exclude(), GameSettings.tickDelayRender);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void processSystem() {
		//if (true) return;
		//Logger.dbg("tick " + this);
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		Entity player = Game_AI_TestBed.instance().getLevel().getPlayerEntity();
		
		if (player != null) {
			Health health = mapHealth.get(player);
			
			int x = 10;
			int y = 30;
			int size = health.hp;
			int sizeMax = health.hpMax;
			int width = 20;
			int borderSize = 2;
			
			ShapeRenderer healthBoxOutline = new ShapeRenderer();
			//orient to map coords
			//healthBox.setProjectionMatrix(game.getCamera().combined);
			healthBoxOutline.begin(ShapeType.Filled);
			healthBoxOutline.setColor(0, 1, 0, 0);
			healthBoxOutline.rectLine(x - borderSize, y, x + sizeMax + borderSize, y, width + borderSize*2);
			healthBoxOutline.end();
			
			healthBoxOutline.dispose();
			
			ShapeRenderer healthBox = new ShapeRenderer();
			//orient to map coords
			//healthBox.setProjectionMatrix(game.getCamera().combined);
			healthBox.begin(ShapeType.Filled);
			healthBox.setColor(1, 0, 0, 0);
			healthBox.rectLine(x, y, x + size, y, width);
			healthBox.end();
			
			healthBox.dispose();
		}
		
	}

	@Override
	protected void process(Entity e) {
		
	}

}
