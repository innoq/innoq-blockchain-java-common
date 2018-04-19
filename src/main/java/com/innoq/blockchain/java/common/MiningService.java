package com.innoq.blockchain.java.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MiningService {

  private final String hashPrefix;

  private final MessageDigest digest;

  private final ObjectMapper objectMapper;

  public MiningService(String hashPrefix) throws Exception {
    this.hashPrefix = hashPrefix;
    digest = MessageDigest.getInstance("SHA-256");
    objectMapper = new ObjectMapper();
  }

  public MiningResult mine(int newBlockIndex, List<com.innoq.blockchain.java.common.Transaction> transactions, byte[] lastBlock) throws Exception {
    Instant miningStart = Instant.now();

    int proofCounter = -1;
    String hash = null;
    final String lastBlockHash = hash(lastBlock);
    byte[] block = null;
    do {
      proofCounter++;
      block = createBlock(newBlockIndex, miningStart, proofCounter, transactions, lastBlockHash);
      hash = hash(block);
    } while (!hash.startsWith(hashPrefix));

    Duration duration = Duration.between(miningStart, Instant.now());
    double hashesPerSecond = (proofCounter + 1.0) / (duration.getSeconds() + 1);
    return new MiningResult(duration, hashesPerSecond, block);
  }

  private byte[] createBlock(int index, Instant timestamp, int proof, List<com.innoq.blockchain.java.common.Transaction> transactions, String previousBlockHash) throws JsonProcessingException {
    Block block = new Block();
    block.index = index;
    block.timestamp = timestamp.toEpochMilli();
    block.proof = proof;
    block.transactions = transactions.stream().map(tc -> {
      Block.Transaction transaction = new Block.Transaction();
      transaction.id = tc.id;
      transaction.payload = tc.payload;
      transaction.timestamp = tc.timestamp;
      return transaction;
    })
        .collect(toList());
    block.previousBlockHash = previousBlockHash;

    return objectMapper.writeValueAsBytes(block);
  }

  public String hash(byte[] block) {
    return String.format("%064x", new BigInteger(1, digest.digest(block)));
  }

  /* serialization classes */
  static class Block {

    public int index;
    public long timestamp;
    public int proof;
    public List<Transaction> transactions;
    public String previousBlockHash;

    static class Transaction {

      public String id;
      public long timestamp;
      public String payload;
    }
  }

}
