package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.noderegisty.Node;

public class NodeRegisterReponse {
  public String message;
  public Node node;

  public NodeRegisterReponse() {
  }

  public NodeRegisterReponse(String message, Node node) {
    this.message = message;
    this.node = node;
  }
}
