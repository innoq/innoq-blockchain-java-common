package com.innoq.blockchain.java.common;

import com.innoq.blockchain.java.common.coordinator.Coordinator;
import com.innoq.blockchain.java.common.events.EventRepository;
import com.innoq.blockchain.java.common.implementation.BlockChainService;
import com.innoq.blockchain.java.common.implementation.MiningService;
import com.innoq.blockchain.java.common.implementation.TransactionRepository;
import com.innoq.blockchain.java.common.noderegisty.NodeRegistry;

public class Module {

  public final BlockChain blockChain;
  public final NodeRegistry nodeRegistry;
  public final TransactionRepository transactionRepository;
  public final EventRepository eventRepository;
  public final Coordinator coordinator;

  public Module(int prefixLength) {
    eventRepository=new EventRepository();
    transactionRepository = new TransactionRepository();
    nodeRegistry=new NodeRegistry();
    blockChain=new BlockChainService(transactionRepository, nodeRegistry, eventRepository, new MiningService(new byte[prefixLength]));
    coordinator=new Coordinator(nodeRegistry, eventRepository, transactionRepository, blockChain);
  }


}
