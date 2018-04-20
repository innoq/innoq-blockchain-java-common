package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.BlockList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class to manage the persistence of Blocks
 *
 * @author thorstenk
 */
public class BlockRepository {

  private final List<Block> blocks;

  public BlockRepository() {
    blocks = new ArrayList<>();
    persist(GenesisBlock.get());
  }

  public Stream<Block> getBlocks() {
    return blocks.stream();
  }

  public Block getLastBlock() {
    return blocks.get(blocks.size() - 1);
  }

  public void persist(Block block) {
    blocks.add(block);
  }

  public int getBlockHeight() {
    return blocks.size();
  }


}
