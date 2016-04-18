package com.mygdx.gopez;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by brad on 4/18/16.
 */
public class Enemy implements Actor{
    float width;
    float height;
    float x;
    float y;
    TextureRegion keyframe;


    public Enemy(float h, float w, float x, float y){
        width = w;
        height = h;
        this.x = x;
        this.y = y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public TextureRegion getKeyframe() {
        return null;
    }

    @Override
    public void act(float deltaTime) {

    }
}
