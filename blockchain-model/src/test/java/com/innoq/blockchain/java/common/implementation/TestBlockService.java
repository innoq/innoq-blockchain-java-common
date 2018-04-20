package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBlockService {

  private BlockChain service;

  @Before
  public void setUp() throws Exception {
    service = new Module(2).blockChain;
  }

  @Test
  public void testBlockServiceNodeId() throws Exception {

    NodeStatus status = service.getStatus();

    String nodeId = status.nodeId;
    assertThat(nodeId).isNotBlank();
    assertThat(service.getStatus().nodeId).isEqualTo(nodeId);
  }

  @Test
  public void testBlockServiceStatus() throws Exception {
    NodeStatus status = service.getStatus();
    assertThat(status.currentBlockHeight).isEqualTo(1);

    service.mineBlock();
    status = service.getStatus();

    assertThat(status.currentBlockHeight).isEqualTo(2);
    assertThat(service.getBlockChain().getBlockHeight()).isEqualTo(2);
  }

  @Test
  public void testTransaction() throws Exception {
    assertThat(service.getBlockChain().getBlocks()).hasSize(1);
    service.addTransaction(new TransactionData("payload one"));
    service.addTransaction(new TransactionData("payload two"));

    service.mineBlock();
    List<Block> blocks = service.getBlockChain().getBlocks();
    assertThat(blocks).hasSize(2);

    List<Block.Transaction> transactions = blocks.get(1).transactions;
    assertThat(transactions)
        .hasSize(2);
    assertThat(transactions.stream().map(t -> t.payload)).containsExactly("payload one", "payload two");

    service.mineBlock();
    blocks = service.getBlockChain().getBlocks();
    assertThat(blocks).hasSize(3);

    assertThat(blocks.get(2).transactions).isEmpty();
  }
}
