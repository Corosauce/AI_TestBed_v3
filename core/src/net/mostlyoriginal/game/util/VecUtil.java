package net.mostlyoriginal.game.util;

import javax.vecmath.Vector2f;

public class VecUtil {

	public static Vector2f getTargetVector(float x, float y, float xTarg, float yTarg) {
		float vecX = xTarg - x;
		float vecY = yTarg - y;
		
		float dist = (float) Math.sqrt(vecX * vecX + vecY * vecY);
		
		return new Vector2f(vecX / dist, vecY / dist);
	}

	public static float getDist(Vector2f parVec1, Vector2f parVec2) {
		float x = parVec1.x - parVec2.x;
		float y = parVec1.y - parVec2.y;
		return (float) Math.sqrt(x * x + y * y);
	}
}
