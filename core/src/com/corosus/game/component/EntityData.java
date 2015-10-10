package com.corosus.game.component;

import com.artemis.Component;
import com.corosus.game.Cst;
import com.corosus.game.entity.EnumEntityType;

/**
 * Used for defining clearly different types of entities, eg: living entity vs projectile
 * 
 * slowly becoming a generic use component for common entity attributes
 *
 */
public class EntityData extends Component {

	public boolean aiControlled = false;
	public boolean inputControlled = false;
	
	public EnumEntityType type = EnumEntityType.SPRITE;
	
	public int sizeDiameter = Cst.COLLIDESIZE_DEFAULT;
	
	//mainly for preventing same team collision of projectile to sprite
	//team 0 will be considered player
	public int team = TEAM_PLAYER;
	
	public static int TEAM_PLAYER = 0;
	public static int TEAM_1 = 1;
	
	public EntityData() {
		
	}
	
	public EntityData(EnumEntityType profileID) {
		this.type = profileID;
	}
	
	public EntityData setTeam(int team) {
		this.team = team;
		return this;
	}
}
