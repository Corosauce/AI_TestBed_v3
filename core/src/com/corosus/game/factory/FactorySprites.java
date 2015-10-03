package com.corosus.game.factory;

import com.artemis.annotations.Bind;
import com.corosus.game.component.EntityData;
import com.corosus.game.component.Health;
import com.corosus.game.component.PhysicsData;
import com.corosus.game.component.Position;
import com.corosus.game.component.ProfileData;
import com.corosus.game.component.RenderData;
import com.corosus.game.component.Velocity;

@Bind({Position.class, Velocity.class, EntityData.class, ProfileData.class, Health.class, RenderData.class, PhysicsData.class})
public interface FactorySprites extends com.artemis.EntityFactory<FactorySprites> {
	
	FactorySprites position(float x, float y);
	FactorySprites velocity(float x, float y);
	
	//expose method, dont need for now
	//@Bind(Position.class) @UseSetter FactorySprites setPos(float x, float y);
}
