package com.innoq.blockchain.java.common.noderegisty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.NodeStatus;
import com.innoq.blockchain.java.common.noderegisty.Node;

import java.net.URL;

public class NodeResolver {

  public Node resolve(String host) {
    try {
      NodeStatus status = new ObjectMapper().readValue(new URL(host).openStream(), NodeStatus.class);
      return new Node(status.nodeId, host);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
