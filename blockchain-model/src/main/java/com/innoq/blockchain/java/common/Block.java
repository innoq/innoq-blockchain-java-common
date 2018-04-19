package com.innoq.blockchain.java.common;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Block implements Cloneable {

  public long index;
  public long timestamp;
  public long proof;
  public List<Transaction> transactions;
  public String previousBlockHash;

  public Block() {}

  public Block(long index, long timestamp, long proof, Collection<Transaction> transactions, String previousBlockHash) {
    this.index = index;
    this.timestamp = timestamp;
    this.proof = proof;
    this.transactions = transactions.stream().map(Transaction::clone).collect(toList());
    this.previousBlockHash = previousBlockHash;
  }

  public Block clone() {
    try {
      return (Block) super.clone();
    } catch (CloneNotSupportedException neverHappens) {
      throw new IllegalStateException(neverHappens);
    }
  }

  public static class Transaction implements Cloneable {

    public String id;
    public long timestamp;
    public String payload;

    public Transaction() {}

    public Transaction(String id, long timestamp, String payload) {
      this.id = id;
      this.timestamp = timestamp;
      this.payload = payload;
    }

    public Transaction clone() {
      try {
        return (Transaction) super.clone();
      } catch (CloneNotSupportedException neverHappens) {
        throw new IllegalStateException(neverHappens);
      }
    }
  }
}
