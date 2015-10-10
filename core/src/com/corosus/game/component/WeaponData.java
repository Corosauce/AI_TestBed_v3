package com.corosus.game.component;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;

/**
 * 
 * @author Corosus
 *
 */
public class WeaponData extends Component {

	//list of simutaneously used weapons
	public List<WeaponLocation> listWeaponSlots = new ArrayList<WeaponLocation>();
	
	public static class WeaponLocation {
		public int activeWeaponIndex = 0;
		
		//list of singularly used weapons that you switch between
		public List<Weapon> listWeapons = new ArrayList<Weapon>();
	}
	
	public static class Weapon {
		
	}
	
	public WeaponData() {
		
	}
	
}
