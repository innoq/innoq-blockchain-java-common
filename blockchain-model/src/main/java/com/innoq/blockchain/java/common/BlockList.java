package com.innoq.blockchain.java.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BlockList implements Iterable<Block> {

  private List<Block> blocks;

  public BlockList() {
  }

  public BlockList(Collection<Block> blocks) {
    this.blocks = new ArrayList<>(blocks);
  }

  public void setBlocks(List<Block> blocks) {
    this.blocks = blocks;
  }

  @Override
  public Iterator<Block> iterator() {
    return blocks.iterator();
  }

  public long getBlockHeight() {
    return blocks.size();
  }

  public void setBlockHeight(long ignore__needed_for_Jackson_currently) {

  }

  public List<Block> getBlocks() {
    return blocks;
  }
}
