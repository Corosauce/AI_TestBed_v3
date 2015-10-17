package com.corosus.game.ai.pathfind;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.Logger;
import com.corosus.game.ai.pathfind.node.FlatTiledNode;
import com.corosus.game.ai.pathfind.node.TiledNode;
import com.corosus.game.util.IntPair;

public class PathfinderHelper {

	public static PathfinderHelper instance;
	
	FlatTiledGraph graph;
	TiledSmoothableGraphPath<FlatTiledNode> path;
	TiledManhattanDistance<FlatTiledNode> heuristic;
	IndexedAStarPathFinder<FlatTiledNode> pathFinder;
	PathSmoother<FlatTiledNode, Vector2> pathSmoother;
	
	public boolean initLevelData = true;
	
	//level data
	//TODO: make level data only be loaded when level loads unless we do dynamic stuff later on(?)
	public int[][] nodes;
	
	public static PathfinderHelper instance() {
		if (instance == null) {
			instance = new PathfinderHelper();
		}
		return instance;
	}
	
	
	
	public List<IntPair> getPath(int levelID, IntPair from, IntPair to) {
		
		
		
		/*graph = new Graph(128);
		path = new DefaultGraphPath<Node>();
		heuristic = new DistHeuristic();
		//pathSmoother = new PathSmoother<>(new Tiled)
		pathSmoother = new PathSmoother<Node, Vector2>(new TiledRaycastCollisionDetector<Node>(graph));*/
		

		
		Level level = Game_AI_TestBed.instance().getLevel(levelID);
		
		if (initLevelData) {
			initLevelData = false;
			
			nodes = new int[level.getTileSizeX()][level.getTileSizeY()];//new FlatTiledNode[level.getTileSizeX()][level.getTileSizeY()];
			for (int x = 0; x < level.getTileSizeX(); x++) {
	            for (int y = 0; y < level.getTileSizeY(); y++) {
	                if (level.isTilePassable(x, y)) {
	                	nodes[x][y] = TiledNode.TILE_FLOOR;//new FlatTiledNode(x*Cst.TILESIZE, y*Cst.TILESIZE, TiledNode.TILE_FLOOR, 4);
	                } else {
	                	nodes[x][y] = TiledNode.TILE_WALL;
	                }
	            }
	        }
			
			graph = new FlatTiledGraph(level.getTileSizeX(), level.getTileSizeY());
			graph.init(nodes);
		}
		
		
		path = new TiledSmoothableGraphPath<FlatTiledNode>();
		heuristic = new TiledManhattanDistance<FlatTiledNode>();
		pathFinder = new IndexedAStarPathFinder<FlatTiledNode>(graph, true);
		pathSmoother = new PathSmoother<FlatTiledNode, Vector2>(new TiledRaycastCollisionDetector<FlatTiledNode>(graph));
		
		FlatTiledNode nodeStart = graph.getNode(from.x, from.y);
		FlatTiledNode nodeEnd = graph.getNode(to.x, to.y);
		
		path.clear();
		
		graph.startNode = nodeStart;
		pathFinder.searchNodePath(nodeStart, nodeEnd, heuristic, path);
		
		//smoooooooth
		pathSmoother.smoothPath(path);
		
		//Logger.dbg("nodes: " + path.nodes.size);
		
		List<IntPair> listPaths = new ArrayList<IntPair>();
		for (FlatTiledNode node : path.nodes) {
            listPaths.add(new IntPair(node.x, node.y));
            //System.out.println(node);
        }
		return listPaths;
		
        /*int index = 0;
        for (int x = 0; x < level.getTileSizeX(); x++) {
            for (int y = 0; y < level.getTileSizeY(); y++) {
                if (level.isTilePassable(x, y)) {
                	nodes[x][y] = new Node(x*Cst.TILESIZE,
                                                y*Cst.TILESIZE,
                                                index++);

                	graph.addNode(nodes[x][y]);
                }
            }
        }
        
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[0].length; y++) {
                if (null != nodes[x][y]) {
                    addNodeNeighbour(nodes, nodes[x][y], x - 1, y);
                    addNodeNeighbour(nodes, nodes[x][y], x + 1, y);
                    addNodeNeighbour(nodes, nodes[x][y], x, y - 1);
                    addNodeNeighbour(nodes, nodes[x][y], x, y + 1);
                }
            }
        }*/
		
		
		//return calcPath(from, to);
	}
	
	/*private void addNodeNeighbour(Node[][] nodes, Node aNode, int aX, int aY) {
        if (aX >= 0 && aX < nodes.length && aY >=0 && aY < nodes[0].length) {
            aNode.addNeighbour(nodes[aX][aY]);
        }
    }
	
	private List<IntPair> calcPath(IntPair from, IntPair to) {
		List<IntPair> listPaths = new ArrayList<IntPair>();
		try {
			path.clear();
			
			Node startNode = nodes[from.x][from.y];
			Node endNode = nodes[to.x][to.y];

	        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
	        
	        
	        
	        for (Node node : path.nodes) {
	            listPaths.add(new IntPair(node.x, node.y));
	            //System.out.println(node);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return listPaths;
	}*/
	
}
