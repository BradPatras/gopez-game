package com.mygdx.gopez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private AssetManager assets;
	private TextureAtlas atlas;
	private float elapsed;
	OrthographicCamera camera;

	Animation animation;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("gpz_run1.png");
		assets = new AssetManager();
		assets.load("gpzrun.pack", TextureAtlas.class);
		assets.finishLoading();

		camera = new OrthographicCamera(135,200);

		atlas = assets.get("gpzrun.pack");
		Array<TextureAtlas.AtlasRegion> runAnimations = atlas.getRegions();
		runAnimations.reverse();
		animation = new Animation(1f/8f,runAnimations);
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(.5f,.5f,.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(animation.getKeyFrame(elapsed, true),0,0);
		batch.end();
	}
}
