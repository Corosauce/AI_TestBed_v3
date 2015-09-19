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

public class SpriteRender extends IntervalEntityProcessingSystem {

	private ComponentMapper<RenderData> mapRender;
	private ComponentMapper<Position> mapPos;
	
	public SpriteRender() {
		super(Aspect.all(Position.class, RenderData.class), GameSettings.tickDelayRender);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void processSystem() {
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		game.batch.setProjectionMatrix(game.getCamera().combined);
		game.batch.begin();
		super.processSystem();
		game.batch.end();
		
	}

	@Override
	protected void process(Entity e) {
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		//TODO: find a way to declare this at init without world NPE
		mapRender = ComponentMapper.getFor(RenderData.class, game.getWorld());
		mapPos = ComponentMapper.getFor(Position.class, Game_AI_TestBed.instance().getWorld());
		
		//Position pos = mapPos.get(e);
		RenderData render = mapRender.get(e);
		Position pos = mapPos.get(e);
		
		//render.anims = GameAssetManager.INSTANCE.getRenderAssets("imgs/sprites/tanya.json");
		
		render.anims.get(render.state).get(render.orient).draw(game.batch, game.stateTime, game.getWorld().getDelta(), pos.x - Cst.SPRITESIZE / 2, pos.y - Cst.SPRITESIZE / 2);
	}

}
