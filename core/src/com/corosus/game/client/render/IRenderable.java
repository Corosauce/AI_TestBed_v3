package com.corosus.game.client.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kickban on 7/29/14.
 */
public interface IRenderable {
    public void draw(SpriteBatch batch, float stateTime, float deltaTime, float x, float y);
}
