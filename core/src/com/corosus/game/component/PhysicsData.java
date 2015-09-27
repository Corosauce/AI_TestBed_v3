package com.corosus.game.component;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.entity.EnumEntityType;
import com.corosus.game.physics.CollisionListener;

/**
 * Used for defining clearly different types of entities, eg: living entity vs projectile
 *
 */
public class PhysicsData extends Component {

	public Body body;
	public CollisionListener collisionListener;
	
	public boolean needInit = true;
	
	public PhysicsData() {
		
	}
	
	public void initPhysics(int entID, float x, float y) {
		World world = Game_AI_TestBed.instance().getLevel().getWorldBox2D();
		
		collisionListener = new CollisionListener();
		
		world.setContactListener(collisionListener);
		
		
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		
		def.position.set(x, y);
		
		
		
		body = world.createBody(def);
		
		/*PolygonShape shape = new PolygonShape();
		shape.setAsBox(Cst.SPRITESIZE/2, Cst.SPRITESIZE/2);*/
		
		CircleShape shape = new CircleShape();
		shape.setRadius(Cst.SPRITECOLLIDESIZE/2);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 1F;
		
		Fixture fixture = body.createFixture(fDef);
		
		//mainly used for looking up who collided with who
		body.setUserData(entID);
		fixture.setUserData(entID);
		
		shape.dispose();
	}
	
	public void dispose() {
		World world = Game_AI_TestBed.instance().getLevel().getWorldBox2D();
		world.destroyBody(body);
	}
}
