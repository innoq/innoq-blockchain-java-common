package com.innoq.blockchain.java.common.noderegisty;

import java.util.Objects;

public class Node {

  public  String nodeId;
  public  String host;

  public Node() {
  }

  public Node(String nodeId, String host) {
    this.nodeId = nodeId;
    this.host = host;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Objects.equals(nodeId, node.nodeId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(nodeId);
  }
}
