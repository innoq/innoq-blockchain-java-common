package com.innoq.blockchain.java.common;

import java.time.Duration;

public class MiningResult {

  public final Duration duration;

  public final double hashesPerSecond;

  public final Block block;

  public MiningResult(final Duration duration, final double hashesPerSecond, final Block block) {
    this.duration = duration;
    this.hashesPerSecond = hashesPerSecond;
    this.block = block;
  }
}