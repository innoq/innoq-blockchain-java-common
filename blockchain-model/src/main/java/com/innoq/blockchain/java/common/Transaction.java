package com.innoq.blockchain.java.common;

public class Transaction {

  public final String id;
  public final String payload;
  public final long timestamp;
  public final boolean confirmed;

  public Transaction(String id, String payload, long timestamp, boolean confirmed) {
    this.id = id;
    this.payload = payload;
    this.timestamp = timestamp;
    this.confirmed = confirmed;
  }

  public Transaction confirm() {
    return new Transaction(id, payload, timestamp, true);
  }
}
