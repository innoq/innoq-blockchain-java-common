package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.noderegisty.Node;

public interface BlockChain {

  NodeStatus getStatus();

  Node addNode(Node node);

  MiningResult mineBlock() throws Exception;

  BlockList getBlockChain();

  Transaction addTransaction(Payload payload);

  Transaction getTransaction(String id);
}
