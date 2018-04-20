package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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

	@PostMapping("/transactions")
	public Transaction transactions(@RequestBody TransactionData data){
		return blockChain.addTransaction(data);
	}

	@GetMapping("/transaction/{id}")
	public Transaction transaction(@PathVariable("id") String id){
		return blockChain.getTransaction(id);
	}

}
