package com.corosus.game.ai.pathfind;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

public class Node implements IndexedNode<Node> {

	public int index;
	public int x;
	public int y;
	
	private Array<Connection<Node>> connections = new Array<Connection<Node>>();
	
	public Node(int x, int y, int index) {
		this.index = index;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Array<Connection<Node>> getConnections() {
		return connections;
	}
	
	public void addNeighbour(Node aNode) {
	    if (null != aNode) {
	        connections.add(new DefaultConnection<Node>(this, aNode));
	    }
	}
	
	@Override
	public String toString() {
		return "x: " + x + ", y: " + y;
	}

}
