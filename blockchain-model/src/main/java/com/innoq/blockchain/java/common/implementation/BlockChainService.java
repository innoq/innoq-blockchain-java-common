package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class BlockChainService implements BlockChain {

  private static final int TRANSACTIONS_WORKLOG_SIZE = 5;

  private final String nodeId;

  private final BlockRepository blockRepository;

  private final TransactionRepository transactionRepository;

  private final MiningService miner;

  public BlockChainService(final MiningService miner) throws Exception {
    nodeId = UUID.randomUUID().toString();
    blockRepository = new BlockRepository();
    transactionRepository = new TransactionRepository();
    this.miner = miner;
  }

  @Override
  public NodeStatus getStatus() {
    return new NodeStatus(nodeId, blockRepository.getBlockHeight());
  }

  @Override
  public MiningResult mineBlock() throws Exception {
    List<Transaction> transactions = transactionRepository.getWorklog()
        .limit(TRANSACTIONS_WORKLOG_SIZE)
        .collect(toList());

    MiningResult result = miner.mine(
        blockRepository.getBlockHeight() + 1,
        transactions.stream().map(t -> new Block.Transaction(t.id, t.timestamp, t.payload)).collect(toList()),
        Hasher.createHash(blockRepository.getLastBlock()));

    blockRepository.persist(result.block);
    transactions.stream().map(Transaction::confirm).forEach(transactionRepository::removeFromWorklog);

    return result;
  }

  @Override
  public BlockList getBlockChain() {
    return new BlockList(blockRepository.getBlocks().collect(toList()));
  }

  @Override
  public Transaction addTransaction(Payload payload) {
    Transaction transaction = new Transaction(
        UUID.randomUUID().toString(),
        payload.payload,
        Instant.now().toEpochMilli());
    transactionRepository.addToWorklog(transaction);
    return transaction;
  }

  @Override
  public Transaction getTransaction(String id) {
    return transactionRepository.getTransaction(id);
  }
}
