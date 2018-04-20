package com.innoq.blockchain.java.common.noderegisty;

import com.innoq.blockchain.java.common.NodeStatus;
import com.innoq.blockchain.java.common.events.Event;
import com.innoq.blockchain.java.common.events.EventRepository;

import java.util.*;

public class NodeRegistry {

  EventRepository eventRepository;

  public NodeRegistry(EventRepository eventRepository) {
    myNodeId = UUID.randomUUID().toString();
    this.eventRepository = eventRepository;
  }

  private final String myNodeId;
  private final Set<Node> neighbours = new HashSet<>();

  public void addNode(Node node) {
    if (!node.nodeId.equals(myNodeId) && neighbours.add(node)) {
      eventRepository.storeEvent(new Event("new_node",node));
    }
  }

  public Collection<Node> getNeighbours() {
    return neighbours;
  }

  public NodeStatus getStatus(int blockHeight) {
    return new NodeStatus(myNodeId, blockHeight, neighbours);
  }

  public String getNodeId() {
    return myNodeId;
  }
}
