package com.corosus.game.client.assets;

/**
 * Created by kickban on 7/9/14.
 */
public class JSONAnimation extends JSONBaseRenderable{
    public String spriteTable;
    public int    row;
    public int    column;
    public int    nframes;
    public float  duration;

    public JSONAnimation(){}

    public JSONAnimation(String spriteTable, int row, int column, int nframes, float duration){
        this.spriteTable = spriteTable;
        this.row         = row;
        this.column      = column;
        this.nframes     = nframes;
        this.duration    = duration;
    }
}
