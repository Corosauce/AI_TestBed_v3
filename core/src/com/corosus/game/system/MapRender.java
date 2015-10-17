package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.component.Position;

public class MapRender extends IntervalEntityProcessingSystem {
	
	public MapRender() {
		super(Aspect.exclude(), GameSettings.tickDelayRender);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void processSystem() {
		
		//Logger.dbg("tick " + this);
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		Level level = game.getActiveLevel();
		
		level.setStateTime(level.getStateTime() + level.getWorld().getDelta());
		
		//game.getCamera().position.x += 1;
		//camera.position.y += 1;
		game.getCamera().zoom = 1.4F;
		game.getCamera().zoom = 3F;
		game.getCamera().update();
		
		ComponentMapper<Position> mapPos = ComponentMapper.getFor(Position.class, level.getWorld());
		Entity player = level.getPlayerEntity();
		
		if (player != null) {
			Position pos = mapPos.get(player);
			
			float partialTick = level.getPartialTick();
			
			float rX = pos.prevX + (pos.x - pos.prevX) * partialTick;
			float rY = pos.prevY + (pos.y - pos.prevY) * partialTick;
			
			game.getCamera().position.x = rX;
			game.getCamera().position.y = rY;
			
			//System.out.println(pos.x + " - " + pos.y);
		}
		
		
		
		Color color = new Color(0, 0, 0, 0);
		
		//clear screen
		Gdx.gl.glClearColor(color.r, color.g,color.b,color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update map position and render
		level.getMapRenderer().setView(game.getCamera());
		level.getMapRenderer().render();
		
	}

	@Override
	protected void process(Entity e) {
		
	}

}
