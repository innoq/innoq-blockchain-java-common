package com.innoq.blockchain.java.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBlockService {

  @Test
  public void testBlockServiceStatus() throws Exception {
    BlockChainService service = new BlockChainService(new MiningService("000"));
    NodeStatus status = service.getStatus();
    assertThat(status.currentBlockHeight).isEqualTo(1);

    service.mineBlock();
    status = service.getStatus();
    assertThat(status.currentBlockHeight).isEqualTo(2);

    assertThat(service.getBlockChain().getBlockHeight()).isEqualTo(2);
  }

  @Test
  public void testBlockServiceNodeId() throws Exception {
    BlockChainService service = new BlockChainService(new MiningService("0"));
    NodeStatus status = service.getStatus();
    String nodeId = status.nodeId;
    assertThat(nodeId).isNotBlank();
    assertThat(service.getStatus().nodeId).isEqualTo(nodeId);
  }


}
