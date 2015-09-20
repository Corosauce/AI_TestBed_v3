package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.Cst;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.client.assets.ActorState;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.client.assets.Orient;
import com.corosus.game.component.Position;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.Velocity;

@Wire
public class SpriteRender extends IntervalEntityProcessingSystem {

	private ComponentMapper<RenderData> mapRender;
	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Velocity> mapVelocity;
	
	public SpriteRender() {
		super(Aspect.all(Position.class, RenderData.class, Velocity.class), GameSettings.tickDelayRender);
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
		
		//Position pos = mapPos.get(e);
		RenderData render = mapRender.get(e);
		Position pos = mapPos.get(e);
		Velocity vel = mapVelocity.get(e);
		
		render.orient = Orient.fromVector(new Vector2(vel.x, vel.y));
		if (vel.x != 0 || vel.y != 0) {
			render.state = ActorState.WALK;
		} else {
			render.state = ActorState.STATIC;
		}
		
		//render.anims = GameAssetManager.INSTANCE.getRenderAssets("imgs/sprites/tanya.json");
		
		render.anims.get(render.state).get(render.orient).draw(game.batch, game.stateTime, game.getWorld().getDelta(), pos.x - Cst.SPRITESIZE / 2, pos.y - Cst.SPRITESIZE / 2);
	}

}