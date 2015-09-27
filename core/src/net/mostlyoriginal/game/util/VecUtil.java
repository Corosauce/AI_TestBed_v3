package net.mostlyoriginal.game.util;

import com.badlogic.gdx.math.Vector2;

public class VecUtil {

	//SWITCH TO VECMATH VECTOR
	public static Vector2 getTargetVector(float x, float y, float xTarg, float yTarg) {
		float vecX = xTarg - x;
		float vecY = yTarg - y;
		
		float dist = (float) Math.sqrt(vecX * vecX + vecY * vecY);
		
		return new Vector2(vecX / dist, vecY / dist);
	}
	
}
