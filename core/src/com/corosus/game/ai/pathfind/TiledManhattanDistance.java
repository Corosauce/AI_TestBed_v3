package com.corosus.game.ai.pathfind;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.corosus.game.ai.pathfind.node.TiledNode;

public class TiledManhattanDistance<N extends TiledNode<N>> implements Heuristic<N> {

	public TiledManhattanDistance () {
	}

	@Override
	public float estimate (N node, N endNode) {
		return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
	}
}
