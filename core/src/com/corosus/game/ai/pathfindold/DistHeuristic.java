package com.corosus.game.ai.pathfindold;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class DistHeuristic implements Heuristic<Node> {

    @Override
    public float estimate(Node node, Node endNode) {
        return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    }

}