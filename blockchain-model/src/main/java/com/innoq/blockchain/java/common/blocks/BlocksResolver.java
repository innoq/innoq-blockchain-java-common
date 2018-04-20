package com.innoq.blockchain.java.common.blocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.BlockList;
import com.innoq.blockchain.java.common.NodeStatus;

import java.net.URL;
import java.util.List;

public class BlocksResolver {

  public BlockList resolve(String host) {
    try {
      return new ObjectMapper().readValue(new URL(host+"/blocks").openStream(), BlockList.class);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
