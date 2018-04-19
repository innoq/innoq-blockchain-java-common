package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Block;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeserializerTest {

  @Test
  public void testRoundtrip() {

    Block block = Deserializer.asBlock(
        Serializer.asBytes(GenesisBlock.get()));

    assertThat(block).isEqualToComparingFieldByFieldRecursively(block);
  }
}