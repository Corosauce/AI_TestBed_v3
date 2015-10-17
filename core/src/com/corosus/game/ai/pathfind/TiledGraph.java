package com.corosus.game.ai.pathfind;

import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.corosus.game.ai.pathfind.node.TiledNode;

public interface TiledGraph<N extends TiledNode<N>> extends IndexedGraph<N> {

	public void init (int[][] data);

	public N getNode (int x, int y);

	public N getNode (int index);

}
