package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.BlockChain;
import com.innoq.blockchain.java.common.Module;
import com.innoq.blockchain.java.common.events.EventRepository;
import com.innoq.blockchain.java.common.implementation.BlockChainService;
import com.innoq.blockchain.java.common.implementation.MiningService;
import com.innoq.blockchain.java.common.noderegisty.NodeRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class InnoqBlockchainJavaSpringApplication {

  private final Module module = new Module(3);

  public static void main(String[] args) {
    SpringApplication.run(InnoqBlockchainJavaSpringApplication.class, args);
  }

  @Bean
  public BlockChain blockChainService() throws Exception {
    return module.blockChain;
  }

  @Bean
  EventRepository eventRepository() {
    return module.eventRepository;
  }

  @Bean
  NodeRegistry nodeRegistry() {
    return module.nodeRegistry;
  }

  @PostConstruct
  public void startCoordinator() {
    new Thread(() -> module.coordinator.eventLoop()).start();
  }
}
