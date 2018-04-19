package com.innoq.blockchain.java.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BlockList implements Iterable<Block> {

  private final List<Block> blocks;

  public BlockList(Collection<Block> blocks) {
    this.blocks = new ArrayList<>(blocks);
  }

  @Override
  public Iterator<Block> iterator() {
    return blocks.iterator();
  }

  public long getBlockHeight() {
    return blocks.size();
  }

  public List<Block> getBlocks() {
    return blocks;
  }
}
