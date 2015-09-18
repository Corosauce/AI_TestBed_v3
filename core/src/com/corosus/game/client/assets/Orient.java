package com.corosus.game.client.assets;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kickban on 7/9/14.
 */
public enum Orient {
    UP(0,1),
    UP_LEFT(-1,0),
    LEFT(-1,0),
    DOWN_LEFT(-1,-1),
    DOWN(0,-1),
    DOWN_RIGHT(1,-1),
    RIGHT(1,0),
    UP_RIGHT(1,-1);

    public float   x, y;
    public Vector2 vec;

    Orient(float x, float y){
        this.x = x;
        this.y = y;
        this.vec = new Vector2(x,y);
    }

    public boolean isHorizontal(){
        return this.x != 0;
    }

    public boolean isVertical(){
        return this.y != 0;
    }

    public static Orient fromAngle(float angle){
        if ((angle >= 22.5) && (angle < 67.5))
            return UP_RIGHT;

        if ((angle >= 67.5) && (angle < 112.5))
            return UP;

        if ((angle >= 112.5) && (angle < 157.5))
            return UP_LEFT;

        if ((angle >= 157.5) && (angle < 202.5))
            return LEFT;

        if ((angle >= 202.5) && (angle < 247.5))
            return DOWN_LEFT;

        if ((angle >= 247.5) && (angle < 292.5))
            return DOWN;

        if ((angle >= 292.5) && (angle < 337.5))
            return DOWN_RIGHT;

        return RIGHT;
    }

    public static Orient fromVector(Vector2 vec){
        return fromAngle(vec.angle());
    }

}
