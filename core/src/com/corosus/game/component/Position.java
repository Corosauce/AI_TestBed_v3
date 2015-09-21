package com.corosus.game.component;

import com.artemis.Component;

public class Position extends Component {

	public float x, y;
	public float rotationYaw = 0;
	
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
