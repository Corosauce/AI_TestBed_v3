package com.corosus.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class Health extends Component {

	public int hp;
	public int hpMax;
	public int lifeTime = 0;
	
	public Health() {
		
	}
	
	public Health(int startHealth) {
		this.hp = startHealth;
		this.hpMax = startHealth;
	}
	
	public boolean isDead() {
		return hp <= 0;
	}
	
	public void reset() {
		this.hp = this.hpMax;
	}
}
