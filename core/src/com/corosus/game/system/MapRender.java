package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corosus.game.Cst;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.component.Position;
import com.corosus.game.component.RenderData;

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
		game.getCamera().zoom = 1F;
		game.getCamera().update();
		
		game.mapRenderer.setView(game.getCamera());
		game.mapRenderer.render();
		
	}

	@Override
	protected void process(Entity e) {
		
	}

}
