package com.innoq.blockchain.java.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

  static MessageDigest sha256Digest = createDigest();

  public static String createHash(Block block) {
    return createHash(Serializer.asBytes(block));
  }

  public static String createHash(byte[] block) {
    return String.format("%064x", new BigInteger(1, sha256Digest.digest(block)));
  }

  private static MessageDigest createDigest() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
