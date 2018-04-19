package com.innoq.blockchain.java.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMiningService {

	private final String prefix = "000";
	
	private MiningService miningService;
	
	@Before
	public void setup() throws Exception {
		miningService = new MiningService(prefix);
	}
	
	@Test
	public void testMinedPrefixHash() throws Exception {
		MiningResult result = miningService.mine(1, Collections.emptyList(), new byte[0]);
		assertThat(result.block).isNotNull();
		assertThat(miningService.hash(result.block)).startsWith(prefix);
	}
	
	@Test
	public void testBlock() throws Exception {
		MiningResult result = miningService.mine(1, Collections.emptyList(), new byte[0]);
		MiningService.Block block = new ObjectMapper().readValue(result.block, MiningService.Block.class);
		assertThat(block.index).isEqualTo(1);
		assertThat(block.previousBlockHash).isEqualTo(miningService.hash(new byte[0]));
		
		result = miningService.mine(2, Collections.emptyList(), new byte[0]);
		block = new ObjectMapper().readValue(result.block, MiningService.Block.class);
		assertThat(block.index).isEqualTo(2);
		assertThat(block.previousBlockHash).isEqualTo(miningService.hash(new byte[0]));
	}
}
