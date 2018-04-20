package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.MiningResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Instant.now;

public class MiningService {

  private final byte[] hashPrefix;
  private final ExecutorService pool;
  private final int noOfThreads;

  public MiningService(byte[] hashPrefix) {
    this.hashPrefix = hashPrefix;
    noOfThreads = Runtime.getRuntime().availableProcessors() - 1;
    pool = Executors.newFixedThreadPool(noOfThreads);
  }


  synchronized public MiningResult mine(int newBlockIndex, List<Block.Transaction> transactions, String previousBlockHash) throws Exception {
    Instant miningStart = now();

    byte[] prefix = String.format("{\"index\":%d,\"timestamp\":%d,\"proof\":", newBlockIndex, now().toEpochMilli()).getBytes(UTF_8);
    byte[] suffix = String.format(",\"transactions\":%s,\"previousBlockHash\":\"%s\"}", Serializer.asString(transactions), previousBlockHash).getBytes(UTF_8);
    AtomicLong proof = new AtomicLong(-1);
    AtomicLong hashes = new AtomicLong();
    CountDownLatch latch = new CountDownLatch(noOfThreads);
    for (int i = 0; i < noOfThreads; i++) {
      int id = i;
      pool.execute(() -> {
        long hashCount = findProof(prefix, suffix, proof, id);
        hashes.getAndAdd(hashCount);
        latch.countDown();
      });
    }
    latch.await();
    Duration duration = Duration.between(miningStart, now());
    double hashesPerMillis = hashes.get() / duration.toMillis();
    return new MiningResult(duration, hashesPerMillis * 1000, Deserializer.asBlock(concatBytes(prefix, Long.toString(proof.get()).getBytes(), suffix)));
  }

  private long findProof(byte[] prefix, byte[] suffix, AtomicLong proof, int id) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    byte[] hash = null;
    long proofCounter = id;
    do {
      digest.reset();
      digest.update(prefix);
      digest.update(Long.toString(proofCounter).getBytes(UTF_8));
      digest.update(suffix);
      hash = digest.digest();
      if (isFittingHash(hash)) {
        proof.set(proofCounter);
      }
      proofCounter += noOfThreads;
    } while (proof.get() == -1);
    return proofCounter / noOfThreads + 1;
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
