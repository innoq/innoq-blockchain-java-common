package com.innoq.blockchain.java.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class to manage the persistence of Blocks
 * @author thorstenk
 *
 */
public class BlockRepository {

	private final List<byte[]> blocks;
	
	public BlockRepository() {
		blocks = new ArrayList<>();
		persistGenesisBlock();
	}
	
	private void persistGenesisBlock() {
		String genesisBlock = "{\"index\":1,\"timestamp\":0,\"proof\":1917336,\"transactions\":[{\"id\":\"b3c973e2-db05-4eb5-9668-3e81c7389a6d\",\"timestamp\":0,\"payload\":\"I am Heribert Innoq\"}],\"previousBlockHash\":\"0\"}";
		persist(genesisBlock.getBytes());
	}
	
	public Stream<byte[]> getBlocks() {
		return blocks.stream();
	}
	
	public byte[] getLastBlock() {
		return blocks.get(blocks.size() - 1);
	}
	
	public void persist(byte[] block) {
		blocks.add(block);
	}
	
	public int getBlockHeight() {
		return blocks.size();
	}
}
