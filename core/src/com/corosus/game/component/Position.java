package com.corosus.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class Position extends Component {

	public float x, y;
	public float prevX, prevY;
	public float rotationYaw = 0;
	
	public Position() {
		
	}
	
	public Position(float x, float y) {
		setPos(x, y);
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		this.prevX = x;
		this.prevY = y;
	}
}
