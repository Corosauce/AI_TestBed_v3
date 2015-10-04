package com.corosus.game;

import javax.vecmath.Vector2f;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.client.input.InputHandler;
import com.corosus.game.client.screen.ScreenActiveGame;
import com.corosus.game.client.screen.TestScreen;
import com.corosus.game.factory.EntityFactory;
import com.corosus.game.system.GameInput;
import com.corosus.game.system.MapRender;
import com.corosus.game.system.SpriteRender;
import com.corosus.game.system.SpriteSim;
import com.corosus.game.system.WorldSim;

public class Game_AI_TestBed extends Game {

	private static Game_AI_TestBed instance;
	
	private InputHandler inputHandler;
	
	/**
	 * testing stuff
	 */
	
	public AssetManager am;
    
	
	private OrthographicCamera camera;
	private Level level;
	
	public static Game_AI_TestBed instance() {
		return instance;
	}
	
	@Override
	public void create() {
		instance = this;
		
		inputHandler = new InputHandler();
		level = new Level();

		restart();
	}
	
	public void restart() {
		
		/**
		 * TEST
		 */
		
		GameAssetManager.INSTANCE.loadAnimations("imgs/sprites/animations.json");
		GameAssetManager.INSTANCE.loadSprites("imgs/sprites/sprites.json");
		GameAssetManager.INSTANCE.loadSounds("sounds/sounds.json");
		
		this.am = new AssetManager();
		this.am.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		this.am.load(getLevel().getLevel(), TiledMap.class);
		this.am.finishLoading();
		
		
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, w, h);
        this.camera.update();
        
        getLevel().restart();
        
        /*world = new World();
        
        world.setSystem(new SpriteSimulate(GameSettings.tickDelayGame));
        world.setSystem(new WorldSys(GameSettings.tickDelayGame));
        world.setSystem(new MapRender());
        world.setSystem(new SpriteRender());
        world.setSystem(new GameInput(GameSettings.tickDelayGame));*/
		
		setScreen(new ScreenActiveGame());
		
		Gdx.input.setInputProcessor(inputHandler);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public boolean isInView(Vector2f pos) {
		return isInView(pos, 0);
	}
	
	public boolean isInView(Vector2f pos, int padding) {
		if (pos.x + padding >= camera.position.x - camera.viewportWidth/2 && 
				pos.x - padding < camera.position.x + camera.viewportWidth/2 && 
				pos.y + padding >= camera.position.y - camera.viewportHeight/2 && 
				pos.y - padding < camera.position.y + camera.viewportHeight/2) {
			return true;
		}
		return false;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public void process(float delta) {
		getLevel().process(delta);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		getLevel().dispose();
	}

}
