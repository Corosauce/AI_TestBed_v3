package com.corosus.game.client.assets;

/**
 * Created by kickban on 7/10/14.
 */
public class JSONSprite  extends JSONBaseRenderable{
    public String spriteTable;
    public int    row;
    public int    column;

    public JSONSprite(){}

    public JSONSprite(String spriteTable, int row, int column){
        this.spriteTable = spriteTable;
        this.row         = row;
        this.column      = column;
    }
}
