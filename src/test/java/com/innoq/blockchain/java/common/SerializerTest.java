package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.implementation.GenesisBlock;
import com.innoq.blockchain.java.common.implementation.Hasher;
import com.innoq.blockchain.java.common.implementation.Serializer;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializerTest {


  @Test
  public void genesisBlock_can_be_serialized() {
    //given
    Block genesisBlock = GenesisBlock.get();

    //when
    byte[] serialized = Serializer.asBytes(genesisBlock);

    //then
    assertThat(serialized).isNotNull();
  }


  @Test
  public void sha256Hash_of_genesisBlock_starts_with_six_zeros() throws NoSuchAlgorithmException {
    //given
    Block genesisBlock = GenesisBlock.get();
    byte[] serialized = Serializer.asBytes(genesisBlock);

    //when
    String hash = Hasher.createHash(serialized);

    //then
    assertThat(hash).startsWith("000000");
  }

}
      
