package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.BlockChain;
import com.innoq.blockchain.java.common.implementation.BlockChainService;
import com.innoq.blockchain.java.common.implementation.MiningService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InnoqBlockchainJavaSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(InnoqBlockchainJavaSpringApplication.class, args);
  }

  @Bean
  public BlockChain blockChainService() throws Exception {
    return new BlockChainService(new MiningService(new byte[2]));
  }
}
