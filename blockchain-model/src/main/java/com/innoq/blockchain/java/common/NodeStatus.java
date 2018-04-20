package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.noderegisty.Node;

import java.util.Collection;

public class NodeStatus {

  public final String nodeId;
  public final int currentBlockHeight;
  public final Collection<Node> neighbours;

  public NodeStatus(String nodeId, int currentBlockHeight, Collection<Node> neighbours) {
    this.nodeId = nodeId;
    this.currentBlockHeight = currentBlockHeight;
    this.neighbours = neighbours;
  }
}
