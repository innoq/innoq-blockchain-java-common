package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.MiningResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMiningService {

  private final byte[] prefix = new byte[2];
  private final String prefixHex = "0000";

  private MiningService miningService;

  @Before
  public void setup() throws Exception {
    miningService = new MiningService(prefix);
  }

  @Test
  public void testMinedPrefixHash() throws Exception {
    MiningResult result = miningService.mine(1, Collections.emptyList(), "");
    System.out.println(result);
    assertThat(result.block).isNotNull();
    assertThat(Hasher.createHash(result.block)).startsWith(prefixHex);
  }

  @Test
  public void testBlock() throws Exception {
    MiningResult result = miningService.mine(1, Collections.emptyList(), "previous block hash");
    System.out.println(result);
    Block block = result.block;
    assertThat(block.index).isEqualTo(1);
    assertThat(block.previousBlockHash).isEqualTo("previous block hash");

    result = miningService.mine(2, Collections.emptyList(), "next previous block hash");
    System.out.println(result);
    block = result.block;
    assertThat(block.index).isEqualTo(2);
    assertThat(block.previousBlockHash).isEqualTo("next previous block hash");
  }
}
