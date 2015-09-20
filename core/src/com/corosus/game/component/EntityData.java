package com.corosus.game.component;

import com.artemis.Component;
import com.corosus.game.entity.EnumEntityType;

/**
 * Used for defining clearly different types of entities, eg: living entity vs projectile
 *
 */
public class EntityData extends Component {

	public boolean aiControlled = false;
	public boolean inputControlled = false;
	
	public EnumEntityType type = EnumEntityType.SPRITE;
	
	public EntityData(EnumEntityType profileID) {
		this.type = profileID;
	}
}
