package com.corosus.game.ai.pathfind.node;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

public abstract class TiledNode<N extends TiledNode<N>> implements IndexedNode<N> {

	/** A constant representing an empty tile */
	public static final int TILE_EMPTY = 0;

	/** A constant representing a walkable tile */
	public static final int TILE_FLOOR = 1;

	/** A constant representing a wall */
	public static final int TILE_WALL = 2;

	/** The x coordinate of this tile */
	public final int x;

	/** The y coordinate of this tile */
	public final int y;

	/** The type of this tile, see {@link #TILE_EMPTY}, {@link #TILE_FLOOR} and {@link #TILE_WALL} */
	public final int type;

	protected Array<Connection<N>> connections;

	public TiledNode (int x, int y, int type, Array<Connection<N>> connections) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.connections = connections;
	}

	@Override
	public abstract int getIndex ();

	@Override
	public Array<Connection<N>> getConnections () {
		return this.connections;
	}

}
