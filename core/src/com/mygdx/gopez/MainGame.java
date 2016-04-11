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
	int bk1;
	int bk2;
	private AssetManager assets;
	private TextureAtlas atlas;
	private TextureAtlas bkAtlas;
	private float elapsed;
	OrthographicCamera camera;
	Animation animation;

	//Physics variables
	float gpz_v;
	float gravity = 1;
	float gpz_y;
	float ground_y;
	boolean jumping;


	@Override
	public void create () {
		Gdx.input.setInputProcessor(new GestureDetector(this));
		batch = new SpriteBatch();
		img = new Texture("gpz_run1.png");
		assets = new AssetManager();
		assets.load("gpzrun.pack", TextureAtlas.class);
		assets.load("gpz_bk.pack", TextureAtlas.class);
		assets.finishLoading();

		bkAtlas = assets.get("gpz_bk.pack");

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		atlas = assets.get("gpzrun.pack");
		Array<TextureAtlas.AtlasRegion> runAnimations = atlas.getRegions();
		runAnimations.reverse();
		animation = new Animation(1f/8f,runAnimations);
		bk1 = -Gdx.graphics.getWidth()/2;

		gpz_v = 0;
		ground_y = (-Gdx.graphics.getHeight()/2) + 260;
		gpz_y = ground_y;

	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		TextureRegion keyframe = animation.getKeyFrame(elapsed, true);
		float newWidth = ((float)Gdx.graphics.getHeight()/2)/((float) bkAtlas.getRegions().get(0).originalHeight) * (bkAtlas.getRegions().get(0).originalWidth);

		batch.draw(bkAtlas.getRegions().get(0),bk1,-Gdx.graphics.getHeight()/2, newWidth,Gdx.graphics.getHeight());

		int leftEdge = (-Gdx.graphics.getWidth()/2) - bkAtlas.getRegions().get(0).originalWidth;

		if(bk1 + newWidth < ((Gdx.graphics.getWidth()/2))) {
			batch.draw(bkAtlas.getRegions().get(0),bk1 + newWidth,-Gdx.graphics.getHeight()/2,newWidth,Gdx.graphics.getHeight());
		} else if (bk1 + newWidth > (Gdx.graphics.getWidth()/2)) {
			batch.draw(bkAtlas.getRegions().get(0),bk1 - newWidth,-Gdx.graphics.getHeight()/2,newWidth,Gdx.graphics.getHeight());
		}

		//if (bk1 < ((-Gdx.graphics.getWidth()/2) - ))

		if (jumping = true) {
			gpz_y += gpz_v;
			gpz_v = gpz_v - gravity;
		}

		if (gpz_y <= ground_y) {
			gpz_v = 0;
			gpz_y = ground_y;
			jumping = false;
		}
		if (jumping){
			batch.draw(atlas.getRegions().get(4), (-Gdx.graphics.getWidth() / 2) + 50, gpz_y, 240, 372);

		} else {
			batch.draw(keyframe, (-Gdx.graphics.getWidth() / 2) + 50, gpz_y, 240, 372);
		}
		batch.end();
		bk1-=10;

		if (Math.abs(bk1) > (Gdx.graphics.getWidth()/2) + newWidth){
			bk1 = -Gdx.graphics.getWidth()/2;
		}
	}


	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (!jumping) {
			gpz_v = 30;
			jumping = true;
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("tap", "x=" + x + "y=" + y);
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
