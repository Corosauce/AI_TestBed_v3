package com.corosus.game.util;

public class IntPair {

	public int x;
	public int y;

	public IntPair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public IntPair(float x, float y) {
		this.x = MathUtil.floorF(x);
		this.y = MathUtil.floorF(y);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
