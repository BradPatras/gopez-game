package com.mygdx.gopez;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by brad on 4/18/16.
 */


public interface Actor {

    float getWidth();
    float getHeight();
    void act(float deltaTime);
    TextureRegion getKeyframe();

}
