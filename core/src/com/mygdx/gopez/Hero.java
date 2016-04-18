package com.mygdx.gopez;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by brad on 4/18/16.
 */


public class Hero implements Actor {

    float width;
    float height;
    float x;
    float y;
    TextureRegion keyframe;
    Array<TextureAtlas.AtlasRegion> runAnimations;
    Array<TextureAtlas.AtlasRegion> dblJumpAnimations;
    TextureRegion jumpKeyframe;
    TextureRegion deadKeyframe;


    public Hero(Array<TextureAtlas.AtlasRegion> run, Array<TextureAtlas.AtlasRegion> dblJump, TextureRegion death, TextureRegion jump){
        runAnimations = run;
        dblJumpAnimations = dblJump;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public void act(float deltaTime) {

    }

    @Override
    public TextureRegion getKeyframe() {
        return null;
    }
}
