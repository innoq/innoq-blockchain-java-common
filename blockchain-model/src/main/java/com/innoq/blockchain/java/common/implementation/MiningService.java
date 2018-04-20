package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.MiningResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Instant.now;

public class MiningService {

  private final byte[] hashPrefix;

  private final MessageDigest digest;

  public MiningService(byte[] hashPrefix) {
    this.hashPrefix = hashPrefix;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  public MiningResult mine(int newBlockIndex, List<Block.Transaction> transactions, String previousBlockHash) throws Exception {
    Instant miningStart = now();

    byte[] hash = null;
    byte[] prefix = String.format("{\"index\":%d,\"timestamp\":%d,\"proof\":", newBlockIndex, now().toEpochMilli()).getBytes(UTF_8);
    byte[] suffix = String.format(",\"transactions\":%s,\"previousBlockHash\":\"%s\"}", Serializer.asString(transactions), previousBlockHash).getBytes(UTF_8);
    long proofCounter = -1;
    do {
      proofCounter++;
      digest.reset();
      digest.update(prefix);
      digest.update(Long.toString(proofCounter).getBytes(UTF_8));
      digest.update(suffix);
      hash = digest.digest();
    } while (!isFittingHash(hash));

    Duration duration = Duration.between(miningStart, now());
    double hashesPerSecond = (proofCounter + 1.0) / (duration.getSeconds() + 1);
    return new MiningResult(duration, hashesPerSecond, Deserializer.asBlock(concatBytes(prefix, Long.toString(proofCounter).getBytes(), suffix)));
  }

  private byte[] concatBytes(byte[]... bytes) {
    byte[] result = new byte[Stream.of(bytes).mapToInt(a -> a.length).sum()];
    int cursor = 0;
    for (byte[] someBytes : bytes) {
      System.arraycopy(someBytes, 0, result, cursor, someBytes.length);
      cursor += someBytes.length;
    }
    return result;
  }

  private boolean isFittingHash(byte[] hash) {
    for (int i = 0; i < hashPrefix.length; i++) {
      if (hash[i] != hashPrefix[i]) {
        return false;
      }
    }
    return true;
  }
}
