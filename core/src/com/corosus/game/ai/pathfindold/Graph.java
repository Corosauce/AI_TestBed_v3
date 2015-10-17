package com.corosus.game.ai.pathfindold;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;

public class Graph extends DefaultIndexedGraph<Node> {
    
	/**
	 * @param aSize Just an estimate of size. Will grow later if needed.
	 */
    public Graph(int aSize) {
        super(aSize);
    }

    public void addNode(Node aNodes) {
        nodes.add(aNodes);
    }

    public Node getNode(int aIndex) {
        return nodes.get(aIndex);
    }

}