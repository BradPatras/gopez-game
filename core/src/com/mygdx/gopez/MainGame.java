package com.mygdx.gopez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import javafx.scene.paint.Color;

public class MainGame extends ApplicationAdapter implements GestureDetector.GestureListener {
	SpriteBatch batch;
	Texture img;
	int bk1;
	private AssetManager assets;
	private TextureAtlas runAtlas;
	private TextureAtlas bkAtlas;
	private TextureAtlas jumpAtlas;
	private TextureAtlas dblJumpAtlas;
	private TextureAtlas enemyAtlas;
	private float elapsed;
	OrthographicCamera camera;
	Animation runAnimation;
	Animation dblJumpAnimation;
	Animation enemyAnimation;

	//Physics
	float gravity = 3;
	float bk_v;
	float ground_y;

	//gpz dimensions and stuff
	float gpz_height;
	float gpz_width;
	float gpz_y;
	float gpz_v;
	float gpz_jump_v;
	int jumps;

	//enemy dimensions
	float enemy_height;
	float enemy_width;
	float enemy_y;
	float enemy_x;
	float enemy_v;

	@Override
	public void create () {
		Gdx.input.setInputProcessor(new GestureDetector(this));
		batch = new SpriteBatch();
		img = new Texture("gpz_run1.png");
		assets = new AssetManager();
		assets.load("gpzrun.pack", TextureAtlas.class);
		assets.load("gpz_bk.pack", TextureAtlas.class);
		assets.load("gpzjump.pack", TextureAtlas.class);
		assets.load("gpzdbljump.pack", TextureAtlas.class);
		assets.load("enemy.pack", TextureAtlas.class);
		assets.finishLoading();

		bkAtlas = assets.get("gpz_bk.pack");
		jumpAtlas = assets.get("gpzjump.pack");
		dblJumpAtlas = assets.get("gpzdbljump.pack");
		runAtlas = assets.get("gpzrun.pack");
		enemyAtlas = assets.get("enemy.pack");

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Array<TextureAtlas.AtlasRegion> enemyAnimations = enemyAtlas.getRegions();
		Array<TextureAtlas.AtlasRegion> runAnimations = runAtlas.getRegions();
		Array<TextureAtlas.AtlasRegion> dblJumpAnimations = dblJumpAtlas.getRegions();
		dblJumpAnimations.reverse();
		runAnimations.reverse();
		dblJumpAnimation = new Animation(1f/16f, dblJumpAnimations);
		runAnimation = new Animation(1f/8f,runAnimations);
		enemyAnimation = new Animation(1f/12f, enemyAnimations);
		bk1 = -Gdx.graphics.getWidth()/2;


		//Calculate and initialize values relative to screen size
		gpz_height = Gdx.graphics.getHeight() * .15f;
		gpz_width = gpz_height * .645f;
		gpz_v = 0;
		ground_y = ((-Gdx.graphics.getHeight() * .8f)/2);
		gpz_jump_v = gpz_height *.15f;
		gravity = gpz_jump_v*.0375f;
		bk_v = gpz_jump_v * .33f;
		gpz_y = ground_y;

		enemy_height = gpz_height * .4f;
		enemy_width = enemy_height;
		enemy_y = 0;
		enemy_x = Gdx.graphics.getWidth() / 2f;
		enemy_v = gpz_jump_v * .85f;

	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		TextureRegion keyframe;
		TextureRegion enemyKeyframe = enemyAnimation.getKeyFrame(elapsed, true);
		batch.begin();

		float newWidth = ((float) Gdx.graphics.getHeight() / 2) / ((float) bkAtlas.getRegions().get(0).originalHeight) * (bkAtlas.getRegions().get(0).originalWidth);

		batch.draw(bkAtlas.getRegions().get(0), bk1, -Gdx.graphics.getHeight() / 2, newWidth, Gdx.graphics.getHeight());

		if (bk1 + newWidth < ((Gdx.graphics.getWidth() / 2))) {
			batch.draw(bkAtlas.getRegions().get(0), bk1 + newWidth, -Gdx.graphics.getHeight() / 2, newWidth, Gdx.graphics.getHeight());
		} else if (bk1 + newWidth > (Gdx.graphics.getWidth() / 2)) {
			batch.draw(bkAtlas.getRegions().get(0), bk1 - newWidth, -Gdx.graphics.getHeight() / 2, newWidth, Gdx.graphics.getHeight());
		}

		batch.draw(enemyKeyframe, 0,0,enemy_width, enemy_height);

		if (jumps > 0) {
			gpz_y += gpz_v;
			gpz_v = gpz_v - gravity;
		}

		if (gpz_y <= ground_y) {
			gpz_v = 0;
			gpz_y = ground_y;
			jumps = 0;
		}
		if (jumps >0){
			if (jumps % 2 != 0) {
				batch.draw(jumpAtlas.getRegions().get(0), (-Gdx.graphics.getWidth() / 2) * .9f, gpz_y, gpz_width, gpz_height);

			} else {
				keyframe = dblJumpAnimation.getKeyFrame(elapsed, true);
				batch.draw(keyframe, (-Gdx.graphics.getWidth() / 2) * .9f, gpz_y, gpz_width, gpz_height);
			}
		} else {
			keyframe = runAnimation.getKeyFrame(elapsed, true);
			batch.draw(keyframe, (-Gdx.graphics.getWidth() / 2)* .9f, gpz_y, gpz_width, gpz_height);
		}

		batch.end();

		bk1-=bk_v;

		if (Math.abs(bk1) > (Gdx.graphics.getWidth()/2) + newWidth){
			bk1 = -Gdx.graphics.getWidth()/2;
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (jumps ==0 ) {
			gpz_v = gpz_jump_v;
		}
		jumps++;
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
