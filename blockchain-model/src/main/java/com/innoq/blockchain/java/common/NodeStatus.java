package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.noderegisty.Node;

import java.util.Collection;

public class NodeStatus {

  public String nodeId;
  public int currentBlockHeight;
  public Collection<Node> neighbours;

  public NodeStatus() {
  }

  public NodeStatus(String nodeId, int currentBlockHeight, Collection<Node> neighbours) {
    this.nodeId = nodeId;
    this.currentBlockHeight = currentBlockHeight;
    this.neighbours = neighbours;
  }
}
