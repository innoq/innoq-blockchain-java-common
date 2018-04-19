package com.innoq.blockchain.java.common;

import java.util.List;

public interface BlockChain {

  NodeStatus getStatus();

  MiningResult mineBlock() throws Exception;

  BlockList getBlockChain();

  Transaction addTransaction(String payload);

  Transaction getTransaction(String id);
}
