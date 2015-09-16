package com.corosus.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.corosus.game.client.screen.TestScreen;
import com.corosus.game.system.SpriteProcessor;
import com.corosus.game.system.WorldSys;

public class Game_AI_TestBed extends Game {

	private static Game_AI_TestBed instance;
	
	private World world;
	
	/**
	 * testing stuff
	 */
	
	public AssetManager am;
    public TiledMap map;
	public String levelName = "test_002.tmx";
	public TiledMapRenderer mapRenderer;
	
	private OrthographicCamera camera;
	
	public static Game_AI_TestBed instance() {
		return instance;
	}
	
	@Override
	public void create() {
		instance = this;

		restart();
	}
	
	public void restart() {
		
		/**
		 * TEST
		 */
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
		worldConfig.setSystem(new SpriteProcessor(GameSettings.tickDelayGame));
		worldConfig.setSystem(new WorldSys(GameSettings.tickDelayGame));
		
		world = new World(worldConfig);
		
		setScreen(new TestScreen());
		
	}
	
	public World getWorld() {
		return world;
	}
	
	public void render(float delta) {

		camera.position.x += 1;
		camera.zoom = 2F;
		camera.update();
		
		mapRenderer.setView(camera);
		mapRenderer.render();
		
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

}
