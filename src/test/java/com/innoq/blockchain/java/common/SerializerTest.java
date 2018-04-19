package com.innoq.blockchain.java.common;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializerTest {


  @Test
  public void genesisBlock_can_be_serialized() {
    //given
    Block genesisBlock = GenesisBlock.get();

    //when
    Optional<byte[]> serialized = Serializer.asBytes(genesisBlock);

    //then
    assertThat(serialized.isPresent());
  }


  @Test
  public void sha256Hash_of_genesisBlock_starts_with_six_zeros() throws NoSuchAlgorithmException {
    //given
    Block genesisBlock = GenesisBlock.get();
    Optional<byte[]> serialized = Serializer.asBytes(genesisBlock);

    //when
    String hash = Hasher.createHash(serialized.get());

    //then
    assertThat(hash).startsWith("000000");
  }

}
      
