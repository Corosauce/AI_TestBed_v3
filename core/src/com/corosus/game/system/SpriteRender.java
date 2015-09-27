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
import com.corosus.game.Level;
import com.corosus.game.Logger;
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
		
		//Logger.dbg("tick " + this);
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		game.getLevel().getBatch().setProjectionMatrix(game.getCamera().combined);
		game.getLevel().getBatch().begin();
		super.processSystem();
		game.getLevel().getBatch().end();
		
	}

	@Override
	protected void process(Entity e) {
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		//Position pos = mapPos.get(e);
		RenderData render = mapRender.get(e);
		Position pos = mapPos.get(e);
		Velocity vel = mapVelocity.get(e);
		
		//render.orient = Orient.fromVector(new Vector2(vel.x, vel.y));
		
		render.orient = Orient.fromAngleOld(pos.rotationYaw);
		
		if (vel.x != 0 || vel.y != 0) {
			render.state = ActorState.WALK;
		} else {
			render.state = ActorState.STATIC;
		}
		
		//render.anims = GameAssetManager.INSTANCE.getRenderAssets("imgs/sprites/tanya.json");
		
		Level level = game.getLevel();
		
		float partialTick = level.getPartialTick();//(level.getStateTime() - WorldTimer.lastTime) / GameSettings.tickDelayGame;
		
		float rX = pos.prevX + (pos.x - pos.prevX) * partialTick;
		float rY = pos.prevY + (pos.y - pos.prevY) * partialTick;
		
		//Logger.dbg("rx: " + rX + " vs x: " + pos.x + " delta: " + level.getWorld().getDelta() + " state time: " + level.getStateTime() + " partialTick: " + partialTick);
		
		render.anims.get(render.state).get(render.orient).draw(level.getBatch(), level.getStateTime(), level.getWorld().getDelta(), rX - Cst.SPRITESIZE / 2, rY - Cst.SPRITESIZE / 2);
	}

}
