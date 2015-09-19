package com.corosus.game;

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
import com.corosus.game.client.InputHandler;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.client.screen.TestScreen;
import com.corosus.game.system.GameInput;
import com.corosus.game.system.MapRender;
import com.corosus.game.system.SpriteRender;
import com.corosus.game.system.SpriteSimulate;
import com.corosus.game.system.WorldSys;

public class Game_AI_TestBed extends Game {

	private static Game_AI_TestBed instance;
	
	private World world;
	
	private InputHandler inputHandler;
	
	/**
	 * testing stuff
	 */
	
	public AssetManager am;
    public TiledMap map;
	public String levelName = "test_002.tmx";
	public TiledMapRenderer mapRenderer;
	
	private OrthographicCamera camera;
	
	public SpriteBatch batch;
	
	public float stateTime = 0;
	
	public int entityCount = 0;
	
	public static Game_AI_TestBed instance() {
		return instance;
	}
	
	@Override
	public void create() {
		instance = this;
		
		inputHandler = new InputHandler();

		restart();
	}
	
	public void restart() {
		
		/**
		 * TEST
		 */
		
		GameAssetManager.INSTANCE.loadAnimations("imgs/sprites/animations.json");
		GameAssetManager.INSTANCE.loadSprites("imgs/sprites/sprites.json");
		GameAssetManager.INSTANCE.loadSounds("sounds/sounds.json");
		
		batch = new SpriteBatch();
		
		this.am = new AssetManager();
		this.am.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		this.am.load(getLevel(), TiledMap.class);
		this.am.finishLoading();
		this.map = this.am.get(getLevel());
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, w, h);
        this.camera.update();
		
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new SpriteSimulate(GameSettings.tickDelayGame));
		worldConfig.setSystem(new WorldSys(GameSettings.tickDelayGame));
		worldConfig.setSystem(new MapRender());
		worldConfig.setSystem(new SpriteRender());
		worldConfig.setSystem(new GameInput(GameSettings.tickDelayGame));
		
		world = new World(worldConfig);
		
		setScreen(new TestScreen());
		
		Gdx.input.setInputProcessor(inputHandler);
		
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void killEntity(Entity e) {
		getWorld().deleteEntity(e);
		entityCount--;
	}
	
	public void process(float delta) {
		world.setDelta(delta);
		world.process();
	}
	
	public String getLevel() {
		return "maps/" + getLevelName();
	}
	
	public void setLevelName(String name) {
		this.levelName = name;
	}
	
	public String getLevelName() {
		return this.levelName;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		batch.dispose();
	}

}
