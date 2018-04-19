package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        .collect(Collectors.toList());
    MiningService.MiningResult result = miner.mine(
        blockRepository.getBlockHeight() + 1,
        transactions,
        Hasher.createHash(blockRepository.getLastBlock()));
    Block block = Deserializer.asBlock(result.block);
    blockRepository.persist(block);
    transactions.forEach(transactionRepository::removeFromWorklog);

    return new MiningResult(result.duration, result.hashesPerSecond, block);
  }

  @Override
  public BlockList getBlockChain() {
    return new BlockList(blockRepository.getBlocks().collect(Collectors.toList()));
  }

  //  @Override
  public Transaction addTransaction(Payload payload) {
    return new Transaction(
        UUID.randomUUID().toString(),
        payload.payload,
        Instant.now().toEpochMilli(),
        false
    );
  }

  @Override
  public Transaction getTransaction(String id) {
    return transactionRepository.getTransactionConfirmation(id);
  }
}
