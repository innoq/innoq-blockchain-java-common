package com.innoq.blockchain.java.common.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.implementation.MiningService.MiningResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMiningService {

  private final String prefix = "000";

  private MiningService miningService;

  @Before
  public void setup() throws Exception {
    miningService = new MiningService(prefix);
  }

  @Test
  public void testMinedPrefixHash() throws Exception {
    MiningResult result = miningService.mine(1, Collections.emptyList(), "");
    assertThat(result.block).isNotNull();
    assertThat(miningService.hash(result.block)).startsWith(prefix);
  }

  @Test
  public void testBlock() throws Exception {
    MiningResult result = miningService.mine(1, Collections.emptyList(), "previous block hash");
    MiningService.Block block = new ObjectMapper().readValue(result.block, MiningService.Block.class);
    assertThat(block.index).isEqualTo(1);
    assertThat(block.previousBlockHash).isEqualTo("previous block hash");

    result = miningService.mine(2, Collections.emptyList(), "next previous block hash");
    block = new ObjectMapper().readValue(result.block, MiningService.Block.class);
    assertThat(block.index).isEqualTo(2);
    assertThat(block.previousBlockHash).isEqualTo("next previous block hash");
  }
}
