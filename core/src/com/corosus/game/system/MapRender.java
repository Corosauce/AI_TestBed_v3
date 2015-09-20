package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.corosus.game.Cst;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.component.Position;
import com.corosus.game.component.RenderData;

@Wire
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
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		game.stateTime += game.getWorld().getDelta();
		
		//game.getCamera().position.x += 1;
		//camera.position.y += 1;
		game.getCamera().zoom = 2F;
		game.getCamera().update();
		
		Color color = new Color(0, 0, 0, 0);
		
		//clear screen
		Gdx.gl.glClearColor(color.r, color.g,color.b,color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update map position and render
		game.mapRenderer.setView(game.getCamera());
		game.mapRenderer.render();
		
	}

	@Override
	protected void process(Entity e) {
		
	}

}
