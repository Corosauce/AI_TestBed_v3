package com.corosus.game;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.factory.EntityFactory;
import com.corosus.game.system.GameInput;
import com.corosus.game.system.MapRender;
import com.corosus.game.system.PhysicsWrapper;
import com.corosus.game.system.SpriteRender;
import com.corosus.game.system.SpriteSimulate;
import com.corosus.game.system.WorldSim;
import com.corosus.game.system.WorldTimer;

public class Level {

	
	private World worldArtemis;
	private com.badlogic.gdx.physics.box2d.World worldBox2D;
	
	private TiledMap map;
	private String levelName = "test_002.tmx";
	private TiledMapRenderer mapRenderer;
	private SpriteBatch batch;

	
	private long gameTime = 0;
	private float stateTime = 0;
	
	private int entityCount = 0;
	
	private int playerID = -1;

	private int mapTilesX;
	private int mapTilesY;
	private int mapTileWidth;
	private int mapTileHeight;
	
	public void restart() {
		
		setBatch(new SpriteBatch());
		
		loadLevel(getLevel());
		
		setMapRenderer(new OrthogonalTiledMapRenderer(map));
		
		//systems tick in order added by default
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new WorldTimer(GameSettings.tickDelayGame));
		worldConfig.setSystem(new GameInput(GameSettings.tickDelayGame));
		//process world
		worldConfig.setSystem(new WorldSim(GameSettings.tickDelayGame));
		//process physics
		worldConfig.setSystem(new PhysicsWrapper(GameSettings.tickDelayGame));
		//process entities that respond to physics
		worldConfig.setSystem(new SpriteSimulate(GameSettings.tickDelayGame));
		worldConfig.setSystem(new MapRender());
		worldConfig.setSystem(new SpriteRender());
		
		worldArtemis = new World(worldConfig);
		
		worldBox2D = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 0), true);
		
		setPlayer(EntityFactory.createPlayer(100, 100).getId());
	}
	
	public void process(float delta) {
		worldArtemis.setDelta(delta);
		worldArtemis.process();
	}
	
	public void dispose() {
		getBatch().dispose();
	}
	
	public World getWorld() {
		return worldArtemis;
	}
	
	public void killEntity(Entity e) {
		getWorld().deleteEntity(e);
		entityCount--;
	}
	
	public int getEntityCount() {
		return entityCount;
	}
	
	public void setEntityCount(int count) {
		entityCount = count;
	}
	
	public void addToEntityCount(int count) {
		entityCount += count;
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
	
	public void setPlayer(int playerID) {
		this.playerID = playerID;
	}
	
	public Entity getPlayerEntity() {
		if (playerID == -1) {
			System.out.println("PLAYER NOT SET");
			return null;
		}
		return getWorld().getEntity(playerID);
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public TiledMapRenderer getMapRenderer() {
		return mapRenderer;
	}

	public void setMapRenderer(TiledMapRenderer mapRenderer) {
		this.mapRenderer = mapRenderer;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void loadLevel(String levelName) {
		this.map = Game_AI_TestBed.instance().am.get(getLevel());
		
		mapTilesX = map.getProperties().get("width", Integer.class);
        mapTilesY = map.getProperties().get("height", Integer.class);
        mapTileWidth = map.getProperties().get("tilewidth", Integer.class);
        mapTileHeight = map.getProperties().get("tileheight", Integer.class);
	}
	
	public int getLevelSizeX() {
		return mapTilesX * mapTileWidth;
	}
	
	public int getLevelSizeY() {
		return mapTilesY * mapTileHeight;
	}

	public float getPartialTick() {
		return (Game_AI_TestBed.instance().getLevel().getStateTime() - WorldTimer.lastTime) / GameSettings.tickDelayGame;
	}

	public com.badlogic.gdx.physics.box2d.World getWorldBox2D() {
		return worldBox2D;
	}

	public void setWorldBox2D(com.badlogic.gdx.physics.box2d.World worldBox2D) {
		this.worldBox2D = worldBox2D;
	}

	public long getGameTime() {
		return gameTime;
	}

	public void setGameTime(long gameTime) {
		this.gameTime = gameTime;
	}
}
