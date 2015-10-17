package com.corosus.game.ai.pathfindold;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.corosus.game.Cst;
import com.corosus.game.Game_AI_TestBed;
import com.corosus.game.Level;
import com.corosus.game.util.IntPair;

public class PathfinderHelper {

	public static PathfinderHelper instance;
	
	public Graph graph;
	public DefaultGraphPath<Node> path;
	public DistHeuristic heuristic;
	public IndexedAStarPathFinder<Node> pathFinder;
	public Node[][] nodes;
	
	public static PathfinderHelper instance() {
		if (instance == null) {
			instance = new PathfinderHelper();
		}
		return instance;
	}
	
	public List<IntPair> getPath(int levelID, IntPair from, IntPair to) {
		
		
		
		graph = new Graph(128);
		path = new DefaultGraphPath<Node>();
		heuristic = new DistHeuristic();
		
		Level level = Game_AI_TestBed.instance().getLevel(levelID);
		
		
		nodes = new Node[level.getTileSizeX()][level.getTileSizeY()];
		
        int index = 0;
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
        }
		
		pathFinder = new IndexedAStarPathFinder<Node>(graph, true);
		
		
		return calcPath(from, to);
	}
	
	private void addNodeNeighbour(Node[][] nodes, Node aNode, int aX, int aY) {
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
	}
	
}
