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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MainGame extends ApplicationAdapter implements GestureDetector.GestureListener {
	SpriteBatch batch;
	Texture img;
	Texture bk;
	private AssetManager assets;
	private TextureAtlas atlas;
	private TextureAtlas bkAtlas;
	private float elapsed;
	OrthographicCamera camera;
	Animation animation;

	@Override
	public void create () {
		Gdx.input.setInputProcessor(new GestureDetector(this));
		batch = new SpriteBatch();
		img = new Texture("gpz_run1.png");
		assets = new AssetManager();
		assets.load("gpzrun.pack", TextureAtlas.class);
		assets.load("gpz_bkg.pack", TextureAtlas.class);
		assets.finishLoading();

		bkAtlas = assets.get("gpz_bkg.pack");

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		atlas = assets.get("gpzrun.pack");
		Array<TextureAtlas.AtlasRegion> runAnimations = atlas.getRegions();
		Array<TextureAtlas.AtlasRegion> background = bkAtlas.getRegions();
		runAnimations.reverse();
		animation = new Animation(1f/8f,runAnimations);

	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		TextureRegion keyframe = animation.getKeyFrame(elapsed, true);
		batch.draw(bkAtlas.getRegions().get(0),-Gdx.graphics.getWidth()/2,-Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(keyframe,(-Gdx.graphics.getWidth()/2) + 50,(-Gdx.graphics.getHeight()/2) + 260, 240, 372);
		batch.end();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (x < Gdx.graphics.getWidth()/2) {
			for (TextureRegion t : animation.getKeyFrames()) {
				if (!t.isFlipX())
					t.flip(true, false);

			}
		} else {
			for (TextureRegion t : animation.getKeyFrames()) {
				if (t.isFlipX())
					t.flip(true, false);

			}
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
