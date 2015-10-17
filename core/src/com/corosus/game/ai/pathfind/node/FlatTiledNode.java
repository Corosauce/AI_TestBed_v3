package com.corosus.game.ai.pathfind.node;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.corosus.game.ai.pathfind.FlatTiledGraph;

public class FlatTiledNode extends TiledNode<FlatTiledNode> {

	public FlatTiledNode (int x, int y, int type, int connectionCapacity) {
		super(x, y, type, new Array<Connection<FlatTiledNode>>(connectionCapacity));
	}

	@Override
	public int getIndex () {
		return x * FlatTiledGraph.sizeY + y;
	}

}
