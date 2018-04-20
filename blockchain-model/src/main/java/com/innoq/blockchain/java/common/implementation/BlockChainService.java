package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.*;
import com.innoq.blockchain.java.common.events.Event;
import com.innoq.blockchain.java.common.events.EventRepository;
import com.innoq.blockchain.java.common.noderegisty.Node;
import com.innoq.blockchain.java.common.noderegisty.NodeRegistry;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

public class BlockChainService implements BlockChain {

  private static final int TRANSACTIONS_WORKLOG_SIZE = 5;


  private final BlockRepository blockRepository;

  private final TransactionRepository transactionRepository;

  private final NodeRegistry nodeRegistry;

  private final EventRepository eventRepository;

  private final MiningService miner;

  public BlockChainService(final MiningService miner) throws Exception {
    blockRepository = new BlockRepository();
    transactionRepository = new TransactionRepository();
    nodeRegistry = new NodeRegistry();
    eventRepository = new EventRepository();
    this.miner = miner;
  }

  @Override
  public NodeStatus getStatus() {
    return nodeRegistry.getStatus(blockRepository.getBlockHeight());
  }

  @Override
  public Node addNode(Node node) {
    nodeRegistry.addNode(node);
    return node;
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
    eventRepository.storeEvent(new Event(currentTimeMillis(), "new_block", result.block));

    return result;
  }

  @Override
  public BlockList getBlockChain() {
    return new BlockList(blockRepository.getBlocks().collect(toList()));
  }

  @Override
  public Transaction addTransaction(TransactionData payload) {
    Transaction transaction = new Transaction(
        UUID.randomUUID().toString(),
        payload.payload,
        Instant.now().toEpochMilli());
    transactionRepository.addToWorklog(transaction);
    eventRepository.storeEvent(new Event(currentTimeMillis(), "new_block", transaction));
    return transaction;
  }

  @Override
  public Transaction getTransaction(String id) {
    return transactionRepository.getTransaction(id);
  }

  List<Event> getEvents() {
    return eventRepository.getEvents().collect(toList());
  }
}
