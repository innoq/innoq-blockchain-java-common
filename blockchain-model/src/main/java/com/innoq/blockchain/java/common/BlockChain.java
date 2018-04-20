package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.implementation.noderegisty.Node;

public interface BlockChain {

  NodeStatus getStatus();

  NodeStatus addNode(Node node);

  MiningResult mineBlock() throws Exception;

  BlockList getBlockChain();

  Transaction addTransaction(Payload payload);

  Transaction getTransaction(String id);
}
