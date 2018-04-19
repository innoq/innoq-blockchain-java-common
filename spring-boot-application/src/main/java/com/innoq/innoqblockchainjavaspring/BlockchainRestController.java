package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.BlockChain;
import com.innoq.blockchain.java.common.BlockList;
import com.innoq.blockchain.java.common.MiningResult;
import com.innoq.blockchain.java.common.NodeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainRestController {
	BlockChain blockChain;

	@Autowired
	public BlockchainRestController(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	@GetMapping("/")
	public NodeStatus index() {
		return blockChain.getStatus();
	}

	@GetMapping("/blocks")
	public BlockList getBlocks() {
		return blockChain.getBlockChain();
	}

	@GetMapping("/mine")
	public MiningResult mine() throws Exception{
		return blockChain.mineBlock();
	}

}
