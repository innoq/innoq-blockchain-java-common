package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.*;
import com.innoq.blockchain.java.common.events.Event;
import com.innoq.blockchain.java.common.events.EventRepository;
import com.innoq.blockchain.java.common.noderegisty.Node;
import com.innoq.blockchain.java.common.noderegisty.NodeRegistry;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class BlockChainService implements BlockChain {

  private static final int TRANSACTIONS_WORKLOG_SIZE = 5;


  private final BlockRepository blockRepository;

  private final TransactionRepository transactionRepository;

  private final NodeRegistry nodeRegistry;

  private final EventRepository eventRepository;

  private final MiningService miner;

  public BlockChainService(TransactionRepository transactionRepository, NodeRegistry nodeRegistry, EventRepository eventRepository, MiningService miner) {
    this.transactionRepository = transactionRepository;
    this.nodeRegistry = nodeRegistry;
    this.eventRepository = eventRepository;
    this.miner = miner;
    blockRepository = new BlockRepository();
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
  synchronized public MiningResult mineBlock() throws Exception {
    List<Transaction> transactions = transactionRepository.getWorklog()
        .limit(TRANSACTIONS_WORKLOG_SIZE)
        .collect(toList());

    MiningResult result = miner.mine(
        blockRepository.getBlockHeight() + 1,
        transactions.stream().map(t -> new Block.Transaction(t.id, t.timestamp, t.payload)).collect(toList()),
        Hasher.createHash(blockRepository.getLastBlock()));

    blockRepository.persist(result.block);
    transactions.stream().map(Transaction::confirm).forEach(transactionRepository::removeFromWorklog);
    eventRepository.storeEvent(new Event("new_block", result.block));

    return result;
  }

  @Override
  public BlockList getBlockChain() {
    return new BlockList(blockRepository.getBlocks().collect(toList()));
  }

  @Override
  public  void adaptBlockChain(BlockList blocks) {
    blockRepository.setBlocks(blocks.getBlocks());
    List<Transaction> transactions = blocks.getBlocks().stream()
            .flatMap(block -> block.transactions.stream())
            .map(t -> new Transaction(t.id, t.payload, t.timestamp).confirm())
            .collect(toList());

    transactions.forEach(transactionRepository::removeFromWorklog);
    transactionRepository.replaceTransactions(Stream.concat(transactions.stream(), transactionRepository.getWorklog()).collect(toList()));
  }

  @Override
  public Transaction addTransaction(TransactionData payload) {
    Transaction transaction = new Transaction(
        UUID.randomUUID().toString(),
        payload.payload,
        Instant.now().toEpochMilli());
    transactionRepository.addToWorklog(transaction);
    eventRepository.storeEvent(new Event("new_transaction", new Block.Transaction(transaction.id, transaction.timestamp, transaction.payload)));
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
