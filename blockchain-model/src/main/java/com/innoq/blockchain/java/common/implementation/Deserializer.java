package com.innoq.blockchain.java.common.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.Block;

import java.io.IOException;

class Deserializer {

  public static Block asBlock(byte[] block) {
    try {
      return new ObjectMapper().readValue(block, Block.class);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
