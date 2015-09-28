package com.corosus.game.component;

import com.artemis.Component;

public class Health extends Component {

	public int hp;
	public int lifeTime = 0;
	
	public Health(int startHealth) {
		this.hp = startHealth;
	}
	
	public boolean isDead() {
		return hp <= 0;
	}
}
