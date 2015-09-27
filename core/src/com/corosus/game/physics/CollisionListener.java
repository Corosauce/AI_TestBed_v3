package com.corosus.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.system.SpriteSimulate;

public class CollisionListener implements ContactListener {
	@Override
	public void beginContact(Contact contact) {
	    
	    SpriteSimulate sys = Game_AI_TestBed.instance().getLevel().getWorld().getSystem(SpriteSimulate.class);
	    sys.triggerCollisionEvent((Integer)contact.getFixtureA().getUserData(), (Integer)contact.getFixtureB().getUserData());
	}

	@Override
	public void endContact(Contact contact) {
	    
	    SpriteSimulate sys = Game_AI_TestBed.instance().getLevel().getWorld().getSystem(SpriteSimulate.class);
	    sys.triggerCollisionEndEvent((Integer)contact.getFixtureA().getUserData(), (Integer)contact.getFixtureB().getUserData());
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}