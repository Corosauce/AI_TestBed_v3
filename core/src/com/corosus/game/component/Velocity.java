package com.corosus.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class Velocity extends Component {

	public float x, y;
	
	public Velocity() {
		
	}
	
	public Velocity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}
