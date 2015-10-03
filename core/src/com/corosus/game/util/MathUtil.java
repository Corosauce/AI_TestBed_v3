package com.corosus.game.util;

public class MathUtil {

	public static int floorF(float val)
    {
        int i = (int)val;
        return val < (float)i ? i - 1 : i;
    }
	
}
