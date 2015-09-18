package com.corosus.game.client.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kickban on 7/29/14.
 */
public class WrappedAnim implements IRenderable {
    private Animation animation;

    public WrappedAnim(Animation animation){
        this.animation = animation;
    }

    @Override
    public void draw(SpriteBatch batch, float stateTime, float deltaTime, float x, float y) {
        batch.draw(this.animation.getKeyFrame(stateTime, true), x, y);
    }
}
