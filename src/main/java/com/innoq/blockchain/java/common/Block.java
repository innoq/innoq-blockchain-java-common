package com.innoq.blockchain.java.common;

import java.util.List;

public class Block {

  public int index;
  public long timestamp;
  public int proof;
  public List<Transaction> transactions;
  public String previousBlockHash;

  static class Transaction {

    public String id;
    public long timestamp;
    public String payload;
  }
}
