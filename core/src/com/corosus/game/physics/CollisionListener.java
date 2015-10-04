package com.corosus.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.system.SpriteSim;

public class CollisionListener implements ContactListener {
	@Override
	public void beginContact(Contact contact) {
	    
	    SpriteSim sys = Game_AI_TestBed.instance().getLevel().getWorld().getSystem(SpriteSim.class);
	    sys.triggerCollisionEvent((Integer)contact.getFixtureA().getUserData(), (Integer)contact.getFixtureB().getUserData());
	}

	@Override
	public void endContact(Contact contact) {
	    
	    SpriteSim sys = Game_AI_TestBed.instance().getLevel().getWorld().getSystem(SpriteSim.class);
	    sys.triggerCollisionEndEvent((Integer)contact.getFixtureA().getUserData(), (Integer)contact.getFixtureB().getUserData());
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		contact.setEnabled(false);
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
