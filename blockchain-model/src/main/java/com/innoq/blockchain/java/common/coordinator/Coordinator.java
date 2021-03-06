package com.innoq.blockchain.java.common.coordinator;

import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.BlockChain;
import com.innoq.blockchain.java.common.BlockList;
import com.innoq.blockchain.java.common.Transaction;
import com.innoq.blockchain.java.common.blocks.BlocksResolver;
import com.innoq.blockchain.java.common.events.Event;
import com.innoq.blockchain.java.common.events.EventPoller;
import com.innoq.blockchain.java.common.events.EventRepository;
import com.innoq.blockchain.java.common.implementation.TransactionRepository;
import com.innoq.blockchain.java.common.noderegisty.Node;
import com.innoq.blockchain.java.common.noderegisty.NodeRegistry;

import java.util.Objects;

public class Coordinator {

  final NodeRegistry nodeRegistry;
  final EventRepository eventRepository;
  final TransactionRepository transactionRepository;
  final BlockChain blockChain;

  public Coordinator(NodeRegistry nodeRegistry, EventRepository eventRepository, TransactionRepository transactionRepository, BlockChain blockChain) {
    this.nodeRegistry = nodeRegistry;
    this.eventRepository = eventRepository;
    this.transactionRepository = transactionRepository;
    this.blockChain = blockChain;
  }

  public void eventLoop() {
    while (true) {
      nodeRegistry.getNeighbours().forEach(node -> {
        try {
          new EventPoller().poll(node.host).forEach(event -> {
            switch (event.event) {
              case "new_node":
                Node neighbour = (Node) event.data;
                nodeRegistry.addNode(neighbour);
                break;
              case "new_transaction":
                Block.Transaction transaction = (Block.Transaction) event.data;
                if (transactionRepository.getTransaction(transaction.id) == null) {
                  transactionRepository.addToWorklog(new Transaction(transaction.id, transaction.payload, transaction.timestamp));
                  eventRepository.storeEvent(new Event(event.event, event.data));
                }
                break;
              case "new_block":
                Block block = (Block) event.data;
                if (block.index > blockChain.getBlockChain().getBlockHeight()) {
                  BlockList blocks = new BlocksResolver().resolve(node.host);
                  blockChain.adaptBlockChain(blocks);
                  eventRepository.storeEvent(new Event(event.event, event.data));
                }
                break;
              default:
                System.err.println("unkown event type");
            }
          });
        } catch (Exception boo) {
          System.err.println(boo.getMessage());
        }
      });
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        break;
      }
    }
  }
}
