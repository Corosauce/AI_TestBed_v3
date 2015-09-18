package com.corosus.game.client.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by kickban on 7/29/14.
 */
public class WrappedSprite implements IRenderable {
    private TextureRegion textureRegion;

    public WrappedSprite(TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }


    @Override
    public void draw(SpriteBatch batch, float stateTime, float deltaTime, float x, float y) {
        batch.draw(this.textureRegion, x, y);
    }
}
