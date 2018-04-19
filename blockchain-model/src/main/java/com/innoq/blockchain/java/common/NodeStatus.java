package com.innoq.blockchain.java.common;

public class NodeStatus {

	public final String nodeId;
	
	public final int currentBlockHeight;
	
	public NodeStatus(String nodeId, int currentBlockHeight) {
		this.nodeId = nodeId;
		this.currentBlockHeight = currentBlockHeight;
	}
}
