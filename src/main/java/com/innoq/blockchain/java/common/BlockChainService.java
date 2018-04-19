package com.innoq.blockchain.java.common;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    List<TransactionConfirmation> transactionConfirmations = transactionRepository.getWorklog()
        .limit(TRANSACTIONS_WORKLOG_SIZE)
        .collect(Collectors.toList());
    MiningResult result = miner.mine(blockRepository.getBlockHeight() + 1,
        transactionConfirmations.stream().map(tc -> tc.transaction).collect(Collectors.toList()),
        blockRepository.getLastBlock());
    blockRepository.persist(result.block);
    transactionConfirmations.forEach(transactionRepository::removeFromWorklog);

    return result;
  }

  @Override
  public List<byte[]> getBlockChain() {
    return blockRepository.getBlocks().collect(Collectors.toList());
  }

  @Override
  public TransactionConfirmation addTransaction(String payload) {
    Transaction transaction = new Transaction();
    transaction.id = UUID.randomUUID().toString();
    transaction.payload = payload;
    transaction.timestamp = Instant.now().toEpochMilli();

    TransactionConfirmation tc = new TransactionConfirmation();
    tc.transaction = transaction;
    tc.confirmed = false;

    transactionRepository.saveTransaction(tc);
    transactionRepository.addToWorklog(tc);
    return tc;
  }

  @Override
  public TransactionConfirmation getTransaction(String id) {
    return transactionRepository.getTransactionConfirmation(id);
  }
}
