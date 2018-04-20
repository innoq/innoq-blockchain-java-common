package com.innoq.blockchain.java.common.noderegisty;

import com.innoq.blockchain.java.common.NodeStatus;

import java.util.*;

public class NodeRegistry {
  
  private final String myNodeId;
  private final Set<Node> neighbours = new HashSet<>();

  public NodeRegistry() {
    myNodeId = UUID.randomUUID().toString();
  }

  public void addNode(Node node) {
    neighbours.add(node);
  }

  public Collection<Node> getNeighbours() {
    return neighbours;
  }

  public NodeStatus getStatus(int blockHeight) {
    return new NodeStatus(myNodeId, blockHeight, neighbours);
  }

  public String getNodeId() {
    return myNodeId;
  }
}
