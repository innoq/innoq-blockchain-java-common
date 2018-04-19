package com.innoq.blockchain.java.common;

import java.util.List;

public interface BlockChain {

  NodeStatus getStatus();

  MiningResult mineBlock() throws Exception;

  List<byte[]> getBlockChain();

  TransactionConfirmation addTransaction(String payload);

  TransactionConfirmation getTransaction(String id);
}
