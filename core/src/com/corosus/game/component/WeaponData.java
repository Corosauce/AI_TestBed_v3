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
	public List<WeaponLocation> listWeaponLocations = new ArrayList<WeaponLocation>();
	
	public static class WeaponLocation {
		public int activeWeaponIndex = 0;
		
		//list of singularly used weapons that you switch between
		public List<Weapon> listWeapons = new ArrayList<Weapon>();
	}
	
	
	
	public static class Weapon {
		
		//weapon config
		public int ticksCooldownRate = 10;
		public String projectileType = "";
		
		//runtime
		public int ticksCooldownCur;
		
		public boolean canFire() {
			return ticksCooldownCur <= 0;
		}
		
		//bad design?
		public void fire() {
			ticksCooldownCur = ticksCooldownRate;
		}
	}
	
	public WeaponData() {
		
	}
	
	public boolean hasPrimaryWeapon() {
		return listWeaponLocations.size() > 0 && listWeaponLocations.get(0).listWeapons.size() > 0;
	}
	
	public Weapon getActivePrimary() {
		if (listWeaponLocations.size() > 0) {
			WeaponLocation loc = listWeaponLocations.get(0);
			if (loc.listWeapons.size() > 0) {
				return loc.listWeapons.get(loc.activeWeaponIndex);
			}
		}
		return null;
	}
	
	public void setActivePrimary(Weapon weapon) {
		listWeaponLocations.get(0).listWeapons.set(0, weapon);
	}
	
}
