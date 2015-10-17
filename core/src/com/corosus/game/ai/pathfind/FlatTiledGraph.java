package com.corosus.game.ai.pathfind;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;
import com.corosus.game.ai.pathfind.node.FlatTiledNode;

public class FlatTiledGraph extends DefaultIndexedGraph<FlatTiledNode> implements TiledGraph<FlatTiledNode> {
	
	//TODO: static sizes, must fix for multiple level handling
	public static int sizeX;
	public static int sizeY;

	public boolean diagonal;
	public FlatTiledNode startNode;

	public FlatTiledGraph (int levelTileSizeX, int levelTileSizeY) {
		super(levelTileSizeX * levelTileSizeY);
		sizeX = levelTileSizeX;
		sizeY = levelTileSizeY;
		this.diagonal = false;
		this.startNode = null;
	}
	
	@Override
	public void init (int[][] data) {
		int map[][] = data;
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				nodes.add(new FlatTiledNode(x, y, map[x][y], 4));
			}
		}

		// Each node has up to 4 neighbors, therefore no diagonal movement is possible
		for (int x = 0; x < sizeX; x++) {
			int idx = x * sizeY;
			for (int y = 0; y < sizeY; y++) {
				FlatTiledNode n = nodes.get(idx + y);
				if (x > 0) addConnection(n, -1, 0);
				if (y > 0) addConnection(n, 0, -1);
				if (x < sizeX - 1) addConnection(n, 1, 0);
				if (y < sizeY - 1) addConnection(n, 0, 1);
			}
		}
	}

	@Override
	public FlatTiledNode getNode (int x, int y) {
		return nodes.get(x * sizeY + y);
	}

	@Override
	public FlatTiledNode getNode (int index) {
		return nodes.get(index);
	}

	private void addConnection (FlatTiledNode n, int xOffset, int yOffset) {
		FlatTiledNode target = getNode(n.x + xOffset, n.y + yOffset);
		if (target.type == FlatTiledNode.TILE_FLOOR) n.getConnections().add(new FlatTiledConnection(this, n, target));
	}

}
