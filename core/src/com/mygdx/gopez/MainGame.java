package com.mygdx.gopez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import javafx.scene.paint.Color;

public class MainGame extends ApplicationAdapter implements GestureDetector.GestureListener {

	public interface Callback {
		void onGameOver(int score);
	}


	private Callback callback;
	SpriteBatch batch;
	Texture img;
	float bk1;
	private AssetManager assets;
	private TextureAtlas runAtlas;
	private TextureAtlas bkAtlas;
	private TextureAtlas jumpAtlas;
	private TextureAtlas dblJumpAtlas;
	private TextureAtlas enemyAtlas;
	private TextureAtlas deadAtlas;
	private float elapsed;
	OrthographicCamera camera;
	Animation runAnimation;
	Animation dblJumpAnimation;
	Animation enemyAnimation;

	int scoreValue;
	Label score;
	Label.LabelStyle textStyle;
	BitmapFont font;

	boolean gameOver;

	//Physics
	float gravity = 3;
	float bk_v;
	float ground_y;
	Random r;

	//gpz dimensions and stuff
	float gpz_height;
	float gpz_width;
	float gpz_y;
	float gpz_x;
	float gpz_v;
	float gpz_jump_v;
	int jumps;
	boolean gpz_dead;

	//enemy dimensions
	float enemy_height;
	float enemy_width;
	float enemy_y;
	float enemy_x;
	float enemy_v;
	boolean enemy_dead;

	float gameHeight;
	float gameWidth;

	Music her;

	public void setCallback(Callback c){
		this.callback = c;
	}

	@Override
	public void dispose() {
		super.dispose();
		her.dispose();
	}

	@Override
	public void pause() {
		super.pause();
		her.pause();
	}

	@Override
	public void resume() {
		super.resume();
		her.play();
		her.setVolume(.1f);
		her.setLooping(true);
	}

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
		assets.load("gpzdead.pack", TextureAtlas.class);
		assets.finishLoading();

		gameHeight = Gdx.graphics.getHeight();
		gameWidth = Gdx.graphics.getWidth();

		her = Gdx.audio.newMusic(Gdx.files.internal("her.mp3"));
		bkAtlas = assets.get("gpz_bk.pack");
		jumpAtlas = assets.get("gpzjump.pack");
		dblJumpAtlas = assets.get("gpzdbljump.pack");
		runAtlas = assets.get("gpzrun.pack");
		enemyAtlas = assets.get("enemy.pack");
		deadAtlas = assets.get("gpzdead.pack");

		camera = new OrthographicCamera(gameWidth,gameHeight);
		Array<TextureAtlas.AtlasRegion> enemyAnimations = enemyAtlas.getRegions();
		Array<TextureAtlas.AtlasRegion> runAnimations = runAtlas.getRegions();
		Array<TextureAtlas.AtlasRegion> dblJumpAnimations = dblJumpAtlas.getRegions();
		dblJumpAnimations.reverse();
		runAnimations.reverse();
		dblJumpAnimation = new Animation(1f/16f, dblJumpAnimations);
		runAnimation = new Animation(1f/8f,runAnimations);
		enemyAnimation = new Animation(1f/12f, enemyAnimations);
		bk1 = -gameWidth/2;


		//Calculate and initialize values relative to screen size
		gpz_x = (-gameWidth / 2) * .9f;
		gpz_height = gameHeight * .15f;
		gpz_width = gpz_height * .645f;
		gpz_v = 0;
		ground_y = ((-gameHeight * .8f)/2);
		gpz_jump_v = gpz_height *.15f;
		gravity = gpz_jump_v*.0375f;
		bk_v = gpz_jump_v * .33f;
		gpz_y = ground_y;

		enemy_height = gpz_height * .4f;
		enemy_width = enemy_height;
		enemy_y = 0;
		enemy_x = gameWidth / 2f;
		enemy_v = gpz_jump_v * .4f;
		her.play();
		her.setLooping(true);
		her.setVolume(.1f);
		r = new Random();
		enemy_dead = false;

		font = new BitmapFont();
		textStyle = new Label.LabelStyle();
		textStyle.font = font;
		score = new Label("Become your own hero", textStyle);
		score.setBounds(0, gameHeight/2 - ((gameHeight/2) - ground_y),gameWidth, 2);
		score.setFontScale(1f,1f);

		scoreValue =0;

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

			float newWidth = (gameHeight / 2f) / ((float) bkAtlas.getRegions().get(0).originalHeight) * (bkAtlas.getRegions().get(0).originalWidth);

			batch.draw(bkAtlas.getRegions().get(0), bk1, -gameHeight / 2, newWidth, gameHeight);

			if (bk1 + newWidth < ((gameWidth / 2))) {
				batch.draw(bkAtlas.getRegions().get(0), bk1 + newWidth, -gameHeight / 2, newWidth, gameHeight);
			} else if (bk1 + newWidth > (gameWidth / 2)) {
				batch.draw(bkAtlas.getRegions().get(0), bk1 - newWidth, -gameHeight / 2, newWidth, gameHeight);
			}

		if (!gameOver) {

			if (enemy_x + enemy_width * 3 < -gameWidth / 2f || enemy_y + enemy_height < -gameHeight / 2) {
				spawnEnemy();
			}

			if (enemy_dead) {

				enemy_y -= gravity * 10;
			} else {
				enemy_x = enemy_x - enemy_v;
				checkCollision();
			}
			batch.draw(enemyKeyframe, enemy_x, enemy_y, enemy_width, enemy_height);


			if (!gpz_dead) {

				if (jumps > 0) {
					gpz_y += gpz_v;
					gpz_v = gpz_v - gravity;
				}

				if (gpz_y <= ground_y) {
					gpz_v = 0;
					gpz_y = ground_y;
					jumps = 0;
				}

				if (jumps > 0) {
					if (jumps % 2 != 0) {
						batch.draw(jumpAtlas.getRegions().get(0), gpz_x, gpz_y, gpz_width, gpz_height);

					} else {
						keyframe = dblJumpAnimation.getKeyFrame(elapsed, true);
						batch.draw(keyframe, gpz_x, gpz_y, gpz_width, gpz_height);
					}
				} else {
					keyframe = runAnimation.getKeyFrame(elapsed, true);
					batch.draw(keyframe, gpz_x, gpz_y, gpz_width, gpz_height);
				}
			} else {

				if (gpz_y + gpz_height <= -gameHeight / 2) {
					gameOver = true;
					callback.onGameOver(scoreValue);
				} else {

					gpz_y += gpz_v;
					gpz_v = gpz_v - gravity;

					batch.draw(deadAtlas.getRegions().get(0), gpz_x, gpz_y, gpz_width, gpz_height);
				}

			}


			bk1 -= bk_v;

			if (Math.abs(bk1) > (gameWidth / 2) + newWidth) {
				bk1 = -gameWidth / 2f;
			}
		}
		batch.end();
	}

	private void spawnEnemy(){
		Gdx.app.log("jump v = ",gpz_jump_v + "") ;

		Gdx.app.log("enemy v = ",enemy_v + "") ;
		if (enemy_v >= gpz_jump_v*.8f) {
			enemy_v = gpz_jump_v * .8f;
		}else{
			enemy_v += gpz_jump_v/(enemy_v*20);
		}
		enemy_x =  gameWidth/2f;
		enemy_y = -r.nextInt(Math.abs((int)(ground_y/2f))) + ground_y/2f;
		enemy_dead = false;

	}

	private void checkCollision(){
		if (Math.abs((gpz_x+(gpz_width/2f)) - (enemy_x+(enemy_width/2))) < gpz_width/2f){
			if(Math.abs((gpz_y+(gpz_height/2f)) - (enemy_y+(enemy_height/2))) < gpz_height/2f){
				if (jumps >0 && jumps % 2 == 0){
					gpz_v = gpz_jump_v*.2f;
					jumps--;
					enemy_dead = true;
				} else {
					gpz_dead = true;

					jumps = 0;
					gpz_v = 0;
				}
		}
	}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (jumps ==0 ) {
			gpz_v = gpz_jump_v;
		} else if (jumps % 2 != 0) {
			gpz_v -= gpz_jump_v/2f;
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
