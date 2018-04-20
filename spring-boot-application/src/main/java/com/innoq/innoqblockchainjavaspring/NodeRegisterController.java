package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.NodeStatus;
import com.innoq.blockchain.java.common.noderegisty.Node;
import com.innoq.blockchain.java.common.noderegisty.NodeRegistry;
import com.innoq.blockchain.java.common.noderegisty.NodeResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NodeRegisterController {

  private NodeRegistry nodeRegistry;
  private NodeResolver nodeResolver = new NodeResolver();

  public NodeRegisterController(NodeRegistry nodeRegistry) {
    this.nodeRegistry = nodeRegistry;
  }

  @PostMapping("/nodes/register")
 	public NodeRegisterReponse transactions(@RequestBody NodeRegisterRequest registerRequest){
    NodeStatus nodeStatus = nodeResolver.resolve(registerRequest.host);
    Node node = new Node(nodeStatus.nodeId, registerRequest.host);
    nodeRegistry.addNode(node);
    return new NodeRegisterReponse("New node added", node);
 	}

}
