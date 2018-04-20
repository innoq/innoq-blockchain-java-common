package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.noderegisty.Node;

import java.util.List;

public interface BlockChain {

  NodeStatus getStatus();

  Node addNode(Node node);

  MiningResult mineBlock() throws Exception;

  BlockList getBlockChain();

  Transaction addTransaction(TransactionData payload);

  Transaction getTransaction(String id);
}
