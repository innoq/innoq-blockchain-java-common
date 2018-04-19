package com.innoq.blockchain.java.common;

import java.time.Duration;

public class MiningResult {

	public final Duration duration;
	
	public final double hashesPerSecond;
	
	public final byte[] block;
	
	public MiningResult(final Duration duration, final double hashesPerSecond, final byte[] block) {
		this.duration = duration;
		this.hashesPerSecond = hashesPerSecond;
		this.block = block;
	}
}
