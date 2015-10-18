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
		setStartHealth(startHealth);
	}
	
	public void setStartHealth(int amount) {
		this.hp = amount;
		this.hpMax = amount;
	}
	
	public boolean isDead() {
		return hp <= 0;
	}
	
	public void reset() {
		this.hp = this.hpMax;
	}
}
