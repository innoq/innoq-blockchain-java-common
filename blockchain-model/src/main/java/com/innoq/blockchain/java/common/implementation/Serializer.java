package com.innoq.blockchain.java.common.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.US_ASCII;

class Serializer {

  static public byte[] asBytes(Object block) {
    try {
      return new ObjectMapper().writeValueAsBytes(block);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  static public String asString(Object block) {
    return new String(asBytes(block), US_ASCII);
  }
}
