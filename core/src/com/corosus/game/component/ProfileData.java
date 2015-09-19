package com.corosus.game.component;

import com.artemis.Component;
import com.corosus.game.entity.EnumEntityType;

/**
 * Used for defining how this type of entity will act, variations on same enemy type.
 * Might use this class to define players and types of players
 * 
 * Might use a factory to setup these types of profiles
 * 
 * @author Corosus
 *
 */
public class ProfileData extends Component {
	
	public float moveSpeed = 1;
	public float fireDelay = 10;
	
	public ProfileData() {
		
	}
}
