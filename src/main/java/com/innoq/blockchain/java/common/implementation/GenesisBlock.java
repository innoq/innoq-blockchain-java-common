package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Block;

import java.util.Collections;

class GenesisBlock {

  public static Block get() {
    Block.Transaction transaction = new Block.Transaction("b3c973e2-db05-4eb5-9668-3e81c7389a6d", 0, "I am Heribert Innoq");
    return new Block(1, 0, 1917336, Collections.singleton(transaction), "0");
  }

}
