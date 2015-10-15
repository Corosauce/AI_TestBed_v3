package com.corosus.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.corosus.game.Cst;
import com.corosus.game.GameSettings;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.client.assets.ActorState;
import com.corosus.game.client.assets.Orient;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.Velocity;
import com.corosus.game.entity.EnumEntityType;

public class SpriteRenderHUD extends IntervalEntityProcessingSystem {

	private ComponentMapper<RenderData> mapRender;
	private ComponentMapper<Position> mapPos;
	private ComponentMapper<Velocity> mapVelocity;
	private ComponentMapper<EntityData> mapEntityData;
	private ComponentMapper<Health> mapHealth;
	
	public SpriteRenderHUD() {
		super(Aspect.all(Position.class, RenderData.class, Velocity.class), GameSettings.tickDelayRender);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void processSystem() {
		super.processSystem();
	}

	@Override
	protected void process(Entity e) {
		
		Game_AI_TestBed game = Game_AI_TestBed.instance();
		
		//Position pos = mapPos.get(e);
		RenderData render = mapRender.get(e);
		Position pos = mapPos.get(e);
		Velocity vel = mapVelocity.get(e);
		EntityData entData = mapEntityData.get(e);
		Health health = mapHealth.get(e);
		
		Level level = game.getLevel(entData.levelID);
		
		float partialTick = level.getPartialTick();
		
		float rX = pos.prevX + (pos.x - pos.prevX) * partialTick;
		float rY = pos.prevY + (pos.y - pos.prevY) * partialTick;
		
		//TODO: use batching properly
		
		if (/*false && */game.isInView(pos.toVec(), Cst.SPRITESIZE)) {
			if (health.hp < health.hpMax && entData.type == EnumEntityType.SPRITE && (level.getPlayerEntity() == null || level.getPlayerEntity().getId() != e.getId())) {
				float widthScale = 0.3F;
				float size = health.hp * widthScale;
				float sizeMax = health.hpMax * widthScale;
				float x = (int) rX - (sizeMax/2);
				float y = (int) (rY + 30);
				float width = 10;
				float borderSize = 2;
				
				ShapeRenderer healthBoxOutline = new ShapeRenderer();
				//orient to map coords
				healthBoxOutline.setProjectionMatrix(game.getCamera().combined);
				healthBoxOutline.begin(ShapeType.Filled);
				healthBoxOutline.setColor(0, 1, 0, 0);
				healthBoxOutline.rectLine(x - borderSize, y, x + sizeMax + borderSize, y, width + borderSize*2);
				healthBoxOutline.end();
				
				healthBoxOutline.dispose();
				
				ShapeRenderer healthBox = new ShapeRenderer();
				//orient to map coords
				healthBox.setProjectionMatrix(game.getCamera().combined);
				healthBox.begin(ShapeType.Filled);
				healthBox.setColor(1, 0, 0, 0);
				healthBox.rectLine(x, y, x + size, y, width);
				healthBox.end();
				
				healthBox.dispose();
			}
		}
	}

}
