package com.corosus.game.util;

import javax.vecmath.Vector2f;

import com.badlogic.gdx.maps.Map;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.Logger;

public class VecUtil {

	public static Vector2f getTargetVector(Vector2f source, Vector2f targ) {
		return getTargetVector(source.x, source.y, targ.x, targ.y);
	}
	
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
	
	public static boolean canSee(Vector2f source, Vector2f target) {
		return rayTraceCheap(source, target) == null;
	}
	
	/**
	 * If collision with tilemap, returns hitpoint of tile, if no collision, returns null
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static Vector2f rayTraceCheap(Vector2f source, Vector2f target) {
		Level level = Game_AI_TestBed.instance().getLevel();
		
		float vecX = target.x - source.x;
		float vecY = target.y - source.y;
		float dist = (float) Math.sqrt(vecX * vecX + vecY * vecY);
		
		vecX = vecX / dist;
		vecY = vecY / dist;
		
		float accuracy = 1F;
		
		int steps = 0;
		int stepsMax = 1000;
		float curX = source.x;
		float curY = source.y;
		
		while (steps++ < stepsMax) {
	
			if (getDist(new Vector2f(curX, curY), target) <= accuracy) {
				return null;
			}
			
			if (!level.isPassable((int)curX, (int)curY)) {
				return new Vector2f(curX, curY);
			}
			
			curX += vecX * accuracy;
			curY += vecY * accuracy;
		}
		
		Logger.dbg("hit max iterations for rayTraceCheap, aborted");
		
		return null;
	}
	
	public static float getDist(float x, float x2) {
		float v = x-x2;
		return (float) Math.sqrt(v * v);
	}
}
