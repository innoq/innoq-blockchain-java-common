package com.innoq.blockchain.java.common.noderegisty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.NodeStatus;
import com.innoq.blockchain.java.common.noderegisty.Node;

import java.net.URL;

public class NodeResolver {

  public NodeStatus resolve(String host) {
    try {
      return new ObjectMapper().readValue(new URL(host).openStream(), NodeStatus.class);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
