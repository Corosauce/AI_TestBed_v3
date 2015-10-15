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
import com.corosus.game.Logger;
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
	
	public static byte COLLIDE_TERRAIN = 0x0001;
	public static byte COLLIDE_TEAM0_SPRITE = 0x0002;
	public static byte COLLIDE_TEAM1_SPRITE = 0x0004;
	public static byte COLLIDE_TEAM0_PROJECTILE = 0x0008;
	public static byte COLLIDE_TEAM1_PROJECTILE = 0x0016;
	
	public PhysicsData() {
		
	}
	
	public void initPhysics(int levelID, int entID, float x, float y, int sizeDiameter, short categoryBits, short maskBits, boolean useCCD) {
		World world = Game_AI_TestBed.instance().getLevel(levelID).getWorldBox2D();
		
		/*if (world.isLocked()) {
			System.out.println("CANCELLING PHYSICS, WORLD LOCKED");
			return;
		}*/
		
		collisionListener = new CollisionListener();
		
		world.setContactListener(collisionListener);
		
		
		BodyDef def = new BodyDef();
		
		//use static body on terrain objects, but keep sprites as dynamic
		def.type = BodyType.DynamicBody;
		
		def.position.set(x, y);
		def.bullet = useCCD;
		
		
		//System.out.println("pos: " + x + " - " + y);
		body = world.createBody(def);
		
		/*PolygonShape shape = new PolygonShape();
		shape.setAsBox(Cst.SPRITESIZE/2, Cst.SPRITESIZE/2);*/
		
		CircleShape shape = new CircleShape();
		shape.setRadius(sizeDiameter/2);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 1F;
		fDef.filter.categoryBits = categoryBits;
		fDef.filter.maskBits = maskBits;
		
		Fixture fixture = body.createFixture(fDef);
		
		
		//mainly used for looking up who collided with who
		body.setUserData(entID);
		fixture.setUserData(entID);
		
		shape.dispose();
	}
	
	public void dispose(int levelID) {
		World world = Game_AI_TestBed.instance().getLevel(levelID).getWorldBox2D();
		if (body != null) {
			body.setUserData(null);
			world.destroyBody(body);
			body = null;
		} else {
			//occurs during double kill calls from sprite sim, need some sort of queue remove system? also one for stopping further code processing
			Logger.warn("tried to kill phys body when it was already null'd");
		}
	}
}
