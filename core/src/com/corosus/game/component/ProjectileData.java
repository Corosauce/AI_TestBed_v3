package com.corosus.game.component;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;
import com.corosus.game.entity.ActionRoutine;

/**
 * Used for defining how this type of entity will act, variations on same enemy type.
 * Might use this class to define players and types of players
 * 
 * Might use a factory to setup these types of profiles
 * 
 * @author Corosus
 *
 */
public class ProjectileData extends Component {
	
	//relocate to projectile data
	public float prjDamage = 5;
	
	public ProjectileData() {
		
	}
}
