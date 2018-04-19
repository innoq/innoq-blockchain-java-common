package com.innoq.blockchain.java.common;

import java.util.Collections;

public class GenesisBlock {

  public static Block get() {
    Transaction transaction = new Transaction("b3c973e2-db05-4eb5-9668-3e81c7389a6d", 0, "I am Heribert Innoq");
    return new Block(1, 0, 1917336, Collections.<Transaction>singletonList(transaction), "0");
  }

}
