package com.innoq.blockchain.java.common;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class Serializer {

  static public Optional<byte[]> asBytes(Block block) {
    try {
      return Optional.ofNullable(new ObjectMapper().writeValueAsBytes(block));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  static public Optional<String> asString(Block block) {
    return asBytes(block).map(String::new);
  }
}
