package com.corosus.game;

import javax.vecmath.Vector4f;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.component.Health;
import com.corosus.game.component.Position;
import com.corosus.game.factory.EntityFactory;
import com.corosus.game.factory.spawnable.SpawnableBase;
import com.corosus.game.system.GameInput;
import com.corosus.game.system.HUDRender;
import com.corosus.game.system.MapRender;
import com.corosus.game.system.PhysicsWrapper;
import com.corosus.game.system.SpriteRender;
import com.corosus.game.system.SpriteRenderHUD;
import com.corosus.game.system.SpriteSim;
import com.corosus.game.system.WorldSim;
import com.corosus.game.system.WorldTimer;
import com.corosus.game.util.MathUtil;

public class Level {

	
	private World worldArtemis;
	private com.badlogic.gdx.physics.box2d.World worldBox2D;
	
	private TiledMap map;
	private String levelName = "level_01.tmx";
	private TiledMapRenderer mapRenderer;
	private SpriteBatch batch;

	
	private long gameTime = 0;
	private float stateTime = 0;
	
	private int entityCount = 0;
	
	private int playerID = -1;
	private int levelID = 0;

	private int mapTilesX;
	private int mapTilesY;
	private int mapTileWidth;
	private int mapTileHeight;
	
	public static int LAYER_NAV = 0;
	public static int LAYER_BLOCKINGOBJECTS = 1;
	public static int LAYER_COLLIDE = 2;
	public static int LAYER_MISSIONOBJECTS = 3;
	
	public Level(int levelID) {
		this.levelID = levelID;
	}
	
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
		worldConfig.setSystem(new SpriteSim(GameSettings.tickDelayGame));
		worldConfig.setSystem(new MapRender());
		worldConfig.setSystem(new SpriteRender());
		worldConfig.setSystem(new SpriteRenderHUD());
		worldConfig.setSystem(new HUDRender());
		
		worldArtemis = new World(worldConfig);
		
		worldBox2D = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 0), true);

        loadLevelObjects();
		
		respawnPlayer();
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
	
	public void loadLevelObjects() {
		MapLayer mapLayerMission = getMap().getLayers().get(LAYER_MISSIONOBJECTS);
		
		MapObjects objects = mapLayerMission.getObjects();
		
		for (int i = 0; i < objects.getCount(); i++) {
			MapObject mapObj = objects.get(i);
			
			SpawnableBase base = EntityFactory.getEntity(mapObj.getName());
			if (base != null) {
				base.prepareFromMap(levelID, mapObj);
			}
		}
	}
	
	public void respawnPlayer() {
		Entity ent = getPlayerEntity();
		if (ent != null) {
			Health health = ent.getComponent(Health.class);
			health.reset();
			
			Position pos = ent.getComponent(Position.class);
			pos.setPos(100, 100);
		} else {
			//setPlayer(EntityFactory.createPlayer(100, 100).getId());
			setPlayer(EntityFactory.createEntity_Player(levelID, 100, 100).getId());
		}
		
	}
	
	public int getLevelSizeX() {
		return mapTilesX * mapTileWidth;
	}
	
	public int getLevelSizeY() {
		return mapTilesY * mapTileHeight;
	}

	public float getPartialTick() {
		return (getStateTime() - WorldTimer.lastTime) / GameSettings.tickDelayGame;
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
	
	public Cell getCell(int column, int row, int layer) {
    	Cell cell = getMapLayer(layer).getCell(column, row);
		return cell;
	}
	
	public TiledMapTileLayer getMapLayer(int layer) {
		
		/*MapLayer layer1 = getMap().getLayers().get(0);
		MapLayer layer2 = getMap().getLayers().get(1);
		MapLayer layer3 = getMap().getLayers().get(2);*/
		
		//MapLayers mapLayers = getMap().getLayers();
		
		TiledMapTileLayer mapLayer = (TiledMapTileLayer)getMap().getLayers().get(layer);
		
		//TiledMapTileLayer mapLayerTest = (TiledMapTileLayer)getMap().getLayers().get(LAYER_COLLIDE);
		
		return mapLayer;
	}
	
	public TiledMap getMap() {
		return this.map;
	}
	
	public boolean isPassable(int x, int y) {
		int tileX = MathUtil.floorF((float)x / (float)Cst.TILESIZE);
		int tileY = MathUtil.floorF((float)y / (float)Cst.TILESIZE);
		
		/*if (this.getCell(tileX, tileY, LAYER_COLLIDE) != null) {
			MapProperties props = this.getCell(tileX, tileY, LAYER_COLLIDE).getTile().getProperties();
			if (props.containsKey("width")) {
				System.out.println(props);
			}
		}*/
		
        return this.getCell(tileX, tileY, LAYER_NAV) != null && this.getCell(tileX, tileY, LAYER_COLLIDE) == null;
    }
	
	public Vector4f getCellBorder(int x, int y) {
		int tileX = MathUtil.floorF((float)x / (float)Cst.TILESIZE) * Cst.TILESIZE;
		int tileY = MathUtil.floorF((float)y / (float)Cst.TILESIZE) * Cst.TILESIZE;
		
		//Cell cell = getCell(x / Cst.TILESIZE, y / Cst.TILESIZE, 0);
		return new Vector4f(tileX, tileY, tileX + Cst.TILESIZE, tileY + Cst.TILESIZE);
	}
}
