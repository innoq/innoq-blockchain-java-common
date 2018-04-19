package com.innoq.blockchain.java.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Deserializer {

  public static Block asBlock(byte[] block) {
    try {
      return new ObjectMapper().readValue(block, Block.class);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
